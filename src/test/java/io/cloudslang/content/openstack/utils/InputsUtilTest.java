package io.cloudslang.content.openstack.utils;

import io.cloudslang.content.openstack.exceptions.OpenstackException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.MalformedURLException;

import static io.cloudslang.content.openstack.TestsUtil.getInputsWrapper;
import static io.cloudslang.content.openstack.TestsUtil.setExpectedExceptions;
import static io.cloudslang.content.openstack.compute.entities.ComputeApi.fromString;
import static io.cloudslang.content.openstack.utils.InputsUtil.buildEndpoint;
import static org.junit.Assert.assertEquals;

public class InputsUtilTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldThrowOpenstackExceptionWithSpecificErrorMessage() throws OpenstackException {
        setExpectedExceptions(OpenstackException.class, exception,
                "Invalid Openstack Compute Api: 'Any other value than ComputeApis enum value'. Valid values: 'servers, '.");

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
        assertEquals("https://www.example.com:8774/compute/v2.0/",
                buildEndpoint(getInputsWrapper("https://www.example.com", "api", "v2.0", "ListAllMajorVersions")));
    }
}
