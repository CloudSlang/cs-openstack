package io.cloudslang.content.openstack.utils;

import io.cloudslang.content.openstack.exceptions.OpenstackException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.MalformedURLException;

import static io.cloudslang.content.openstack.TestsUtil.getInputsWrapper;
import static io.cloudslang.content.openstack.compute.entities.ComputeApi.fromString;
import static io.cloudslang.content.openstack.utils.InputsUtil.buildUrl;
import static org.junit.Assert.assertEquals;

public class InputsUtilTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldThrowOpenstackExceptionWithSpecificErrorMessage() throws OpenstackException {
        setExpectedExceptions(OpenstackException.class, exception,
                "Invalid Couchbase ComputeApis: 'Any other value than ComputeApis enum value'. Valid values: 'servers,'.");

        fromString("Any other value than ComputeApis enum value");
    }

    @Test
    public void shouldReturnEmptyWhenApi() throws OpenstackException {
        assertEquals("", fromString("api"));
    }

    @Test
    public void shouldReturnValidValue() throws OpenstackException {
        assertEquals("servers", fromString("servers"));
    }

    @Test
    public void shouldReturnValidEndpoint() throws MalformedURLException, OpenstackException {
        assertEquals("https://www.example.com:8080/compute/v2.0/",
                buildUrl(getInputsWrapper("https://www.example.com:8080", "api", "", "ListAllMajorVersions")));
    }

    @SuppressWarnings({"unchecked", "SameParameterValue"})
    private static void setExpectedExceptions(Class<?> type, ExpectedException exception, String message) {
        exception.expect((Class<? extends Throwable>) type);
        exception.expectMessage(message);
    }
}
