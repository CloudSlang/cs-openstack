package io.cloudslang.content.openstack.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cloudslang.content.openstack.compute.entities.api.Version;
import io.cloudslang.content.openstack.compute.responses.api.ListAllMajorVersionsResponse;
import io.cloudslang.content.openstack.identity.entities.Token;
import io.cloudslang.content.openstack.identity.responses.AuthenticationResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.cloudslang.content.openstack.handlers.ResponseHandler.getHeaderValue;
import static io.cloudslang.content.openstack.handlers.ResponseHandler.handleResponse;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertEquals;

public class ResponseHandlerTest {
    private List<Class<?>> objects = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().create();

    @Before
    public void init() {
        objects = new ArrayList<>();

        objects.add(AuthenticationResponse.class);
        objects.add(ListAllMajorVersionsResponse.class);
    }

    @Test
    public void shouldReturnExpectedToken() {
        assertEquals("gAAAAABZJ8_a7aiq1SnOhbNw8vFb5WZChcvWdzzUAFzhiB99BHrjdSGai--_-JstU3WazsFXmRHNbD07qOQKTp5Sen2R_b9csaDkU49VXqSaJ0jh2nAlwJkys8aazz2oa3xSeUVe3Ndv_HRiW23-iWTr6jquK_AXdhRX7nvM4lmVTrxXFpelnJQ",
                getHeaderValue("HTTP/1.1 201 Created\n" +
                        "Date: Fri, 26 May 2017 06:48:58 GMT\n" +
                        "Server: Apache/2.4.18 (Ubuntu)\n" +
                        "X-Subject-Token: gAAAAABZJ8_a7aiq1SnOhbNw8vFb5WZChcvWdzzUAFzhiB99BHrjdSGai--_-JstU3WazsFXmRHNbD07qOQKTp5Sen2R_b9csaDkU49VXqSaJ0jh2nAlwJkys8aazz2oa3xSeUVe3Ndv_HRiW23-iWTr6jquK_AXdhRX7nvM4lmVTrxXFpelnJQ\n" +
                        "Vary: X-Auth-Token\n" +
                        "X-Distribution: Ubuntu\n" +
                        "x-openstack-request-id: req-0e9239ec-104b-40e0-a337-dca91fb24387\n" +
                        "Content-Length: 521\n" +
                        "Content-Type: application/json", "X-Subject-Token"));
    }

    @Test
    public void shouldReturnEmptyWhenInputIsGarbage() {
        assertEquals("", getHeaderValue(" ~ !@#$%^&*()_+ -=.>,</?|   ", "X-Subject-Token"));
    }

    @Test
    public void shouldReturnEmpty() {
        assertEquals("", handleResponse(null, objects.get(new Random().nextInt(2))));
        assertEquals("", handleResponse("", objects.get(new Random().nextInt(2))));
    }

    @Test
    public void shouldReturnExpectedTimestamp() {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(null, null, null,
                null, null, "", "", "1234567890", "test");

        Token token = new Token.Builder()
                .withExpiresAt("2018-10-01T00:00:00.000000Z")
                .build();

        authenticationResponse.setToken(token);

        assertEquals("2018-10-01T00:00:00.000000Z",
                handleResponse(GSON.toJson(authenticationResponse, AuthenticationResponse.class), AuthenticationResponse.class));
    }

    @Test
    public void shouldCorrectlyHandleListAllMajorVersionsResponse() {
        ListAllMajorVersionsResponse listAllMajorVersionsResponse = new ListAllMajorVersionsResponse(null, "1234567890", null, "", "", "", "");

        Version ver1 = new Version("v1.1", null, "DEPRECATED", "", "", valueOf(System.currentTimeMillis()));
        Version ver3 = new Version("v2.2", null, "SUPPORTED", "", "", valueOf(System.currentTimeMillis()));
        Version ver2 = new Version("v2.5", null, "CURRENT", "", "", valueOf(System.currentTimeMillis()));

        List<Version> versionsList = new ArrayList<>();
        versionsList.add(ver1);
        versionsList.add(ver2);
        versionsList.add(ver3);

        listAllMajorVersionsResponse.setVersions(versionsList);

        assertEquals("v1.1, v2.5, v2.2",
                handleResponse(GSON.toJson(listAllMajorVersionsResponse, ListAllMajorVersionsResponse.class), ListAllMajorVersionsResponse.class));
    }
}
