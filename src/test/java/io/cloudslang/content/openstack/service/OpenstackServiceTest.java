package io.cloudslang.content.openstack.service;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.httpclient.services.HttpClientService;
import io.cloudslang.content.openstack.exceptions.OpenstackException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.MalformedURLException;
import java.util.HashMap;

import static io.cloudslang.content.openstack.TestsUtil.getApiInputs;
import static io.cloudslang.content.openstack.TestsUtil.getCommonInputs;
import static io.cloudslang.content.openstack.TestsUtil.getIdentityInputs;
import static io.cloudslang.content.openstack.TestsUtil.getServerInputs;
import static io.cloudslang.content.openstack.TestsUtil.setExpectedExceptions;
import static io.cloudslang.content.openstack.builders.HttpClientInputsBuilder.buildHttpClientInputs;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpClientService.class, OpenstackService.class})
public class OpenstackServiceTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private HttpClientService httpClientServiceMock;

    private HttpClientInputs httpClientInputs;

    @InjectMocks
    private OpenstackService toTest = new OpenstackService();

    @Before
    public void init() throws Exception {
        whenNew(HttpClientService.class).withAnyArguments().thenReturn(httpClientServiceMock);
        when(httpClientServiceMock.execute(any(HttpClientInputs.class))).thenReturn(new HashMap<>());

        httpClientInputs = buildHttpClientInputs("", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "");

        toTest = new OpenstackService();
    }

    @Test
    public void shouldFailWhenIncorrectEndpoint() throws MalformedURLException, OpenstackException {
        setExpectedExceptions(MalformedURLException.class, exception, "no protocol: not a valid endpoint");

        toTest.execute(httpClientInputs, getCommonInputs("not a valid endpoint", "no matter what", "no matter what", "no matter what"));
    }

    @Test
    public void shouldFailWhenIncorrectApi() throws MalformedURLException, OpenstackException {
        setExpectedExceptions(OpenstackException.class, exception, "Unknown Compute API: incorrect api");

        toTest.execute(httpClientInputs, getCommonInputs("http://example.com", "incorrect api", "no matter what", "no matter what"));
    }

    @Test
    public void shouldMakeHttpCallWhenGetApiVersionDetails() throws MalformedURLException, OpenstackException {
        toTest.execute(httpClientInputs, getCommonInputs("http://example.com", "api", "v2.5", "GetApiVersionDetails"), getApiInputs("v2.1"));

        verify(httpClientServiceMock, times(1)).execute(eq(httpClientInputs));
        verifyNoMoreInteractions(httpClientServiceMock);

        assertEquals("http://example.com:8774/compute/v2.5/v2.1", httpClientInputs.getUrl());
    }

    @Test
    public void shouldMakeHttpCallWhenListAllMajorVersions() throws MalformedURLException, OpenstackException {
        toTest.execute(httpClientInputs, getCommonInputs("http://mycompute.pvt", "api", "v2.0", "ListAllMajorVersions"));

        verify(httpClientServiceMock, times(1)).execute(eq(httpClientInputs));
        verifyNoMoreInteractions(httpClientServiceMock);

        assertEquals("http://mycompute.pvt:8774/compute/v2.0", httpClientInputs.getUrl());
    }

    @Test
    public void shouldMakeHttpCallWhenUnscopedPasswordAuth() throws MalformedURLException, OpenstackException {
        toTest.execute(httpClientInputs,
                getCommonInputs("https://example.com", "identity", "v3", "PasswordAuthenticationWithUnscopedAuthorization"),
                getIdentityInputs(""));

        verify(httpClientServiceMock, times(1)).execute(eq(httpClientInputs));
        verifyNoMoreInteractions(httpClientServiceMock);

        assertEquals("https://example.com:5000/v3/auth/tokens", httpClientInputs.getUrl());
    }

    @Test
    public void shouldMakeHttpCallWhenListServers() throws MalformedURLException, OpenstackException {
        toTest.execute(httpClientInputs,
                getCommonInputs("https://example.com", "servers", "v3", "ListServers"),
                getServerInputs("", "", "", "", "", "2018-09-01T00:00:00Z",
                        "2015-01-24T17:08Z", "", "2018-10-01T08:00:00Z", "", "", "", "",
                        "", "", "", "", "", "", "", "2018-10-01T09:00:00Z",
                        "", "", "","", "", "running", "", "",
                        "", "", "", "", "", "", "",
                        "", "2018-10-02T00:00:00Z", "", "", "active", "", "", "",
                        ""));

        verify(httpClientServiceMock, times(1)).execute(eq(httpClientInputs));
        verifyNoMoreInteractions(httpClientServiceMock);

        assertEquals("https://example.com:8774/compute/v3/servers?changes-since=2015-01-24T17:08Z&created_at=2018-10-01T08:00:00Z&launch_index=10&launched_at=2018-10-01T09:00:00Z&limit=10&power_state=1&progress=10&sort_key=created_at&terminated_at=2018-10-02T00:00:00Z&vm_state=ACTIVE",
                httpClientInputs.getUrl());
    }
}
