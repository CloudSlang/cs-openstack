package io.cloudslang.content.openstack.compute.utils;

import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.MalformedURLException;

import static io.cloudslang.content.openstack.compute.TestsUtil.getInputsWrapper;
import static io.cloudslang.content.openstack.compute.entities.Api.getValue;
import static io.cloudslang.content.openstack.compute.utils.InputsUtil.buildUrl;
import static org.junit.Assert.assertEquals;

public class InputsUtilTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldThrowOpenstackExceptionWithSpecificErrorMessage() throws OpenstackException {
        setExpectedExceptions(OpenstackException.class, exception,
                "Invalid Couchbase Api: 'Any other value than Api enum value'. Valid values: 'servers,'.");

        getValue("Any other value than Api enum value");
    }

    @Test
    public void shouldReturnEmptyWhenApi() throws OpenstackException {
        assertEquals("", getValue("api"));
    }

    @Test
    public void shouldReturnValidValue() throws OpenstackException {
        assertEquals("servers", getValue("servers"));
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
