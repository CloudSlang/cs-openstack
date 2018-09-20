package io.cloudslang.content.openstack.compute.service;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.httpclient.services.HttpClientService;
import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;
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

import static io.cloudslang.content.openstack.compute.TestsUtil.getApiInputs;
import static io.cloudslang.content.openstack.compute.TestsUtil.getCommonInputs;
import static io.cloudslang.content.openstack.compute.TestsUtil.setExpectedExceptions;
import static io.cloudslang.content.openstack.compute.builders.HttpClientInputsBuilder.buildHttpClientInputs;
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
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private HttpClientService httpClientServiceMock;

    private HttpClientInputs httpClientInputs;

    @InjectMocks
    private OpenstackService toTest;

    @Before
    public void init() throws Exception {
        whenNew(HttpClientService.class).withAnyArguments().thenReturn(httpClientServiceMock);
        when(httpClientServiceMock.execute(any(HttpClientInputs.class))).thenReturn(new HashMap<>());

        httpClientInputs = buildHttpClientInputs("", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "", "");

        toTest = new OpenstackService();
    }

    @Test
    public void shouldFailWhenIncorrectEndpoint() throws MalformedURLException, OpenstackException {
        setExpectedExceptions(MalformedURLException.class, exception, "no protocol: not a valid endpoint/");

        toTest.execute(httpClientInputs, getCommonInputs("not a valid endpoint", "no matter what", "no matter what", "no matter what"));
    }

    @Test
    public void shouldFailWhenIncorrectApi() throws MalformedURLException, OpenstackException {
        setExpectedExceptions(OpenstackException.class, exception, "Unknown api: incorrect api");

        toTest.execute(httpClientInputs, getCommonInputs("http://example.com", "incorrect api", "no matter what", "no matter what"));
    }

    @Test
    public void shouldFallbackWhenNoVersion() throws MalformedURLException, OpenstackException {
        toTest.execute(httpClientInputs, getCommonInputs("http://example.com:8080", "servers", "", "no matter what"));

        verify(httpClientServiceMock, times(1)).execute(eq(httpClientInputs));
        verifyNoMoreInteractions(httpClientServiceMock);

        assertEquals("http://example.com:8080/compute/servers/v2.0/", httpClientInputs.getUrl());
    }

    @Test
    public void shouldMakeHttpCallWhenListAllMajorVersions() throws MalformedURLException, OpenstackException {
        toTest.execute(httpClientInputs, getCommonInputs("http://mycompute.pvt:8080", "api", "v3.0", "ListAllMajorVersions"));

        verify(httpClientServiceMock, times(1)).execute(eq(httpClientInputs));
        verifyNoMoreInteractions(httpClientServiceMock);

        assertEquals("http://mycompute.pvt:8080/compute/v3.0/", httpClientInputs.getUrl());
    }

    @Test
    public void shouldMakeHttpCallWhenGetApiVersionDetails() throws MalformedURLException, OpenstackException {
        toTest.execute(httpClientInputs, getCommonInputs("https://example.com:9090", "api", "v2.1", "GetApiVersionDetails"), getApiInputs(""));

        verify(httpClientServiceMock, times(1)).execute(eq(httpClientInputs));
        verifyNoMoreInteractions(httpClientServiceMock);

        assertEquals("https://example.com:9090/compute/v2.1/", httpClientInputs.getUrl());
    }
}
