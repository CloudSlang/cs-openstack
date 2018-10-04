package io.cloudslang.content.openstack;

import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ApiInputsBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.identity.builders.IdentityInputsBuilder;
import org.junit.rules.ExpectedException;

public class TestsUtil {
    private TestsUtil() {
    }

    public static void setExpectedExceptions(Class<?> type, ExpectedException exception, String message) {
        //noinspection unchecked
        exception.expect((Class<? extends Throwable>) type);
        exception.expectMessage(message);
    }

    public static InputsWrapper getInputsWrapper(String endpoint, String api, String version, String action) {
        return new InputsWrapper.Builder()
                .withCommonInputs(getCommonInputs(endpoint, api, version, action))
                .build();
    }

    public static CommonInputsBuilder getCommonInputs(String endpoint, String api, String version, String action) {
        return new CommonInputsBuilder.Builder()
                .withEndpoint(endpoint)
                .withApi(api)
                .withVersion(version)
                .withAction(action)
                .build();
    }

    public static ApiInputsBuilder getApiInputs(String version) {
        return new ApiInputsBuilder.Builder()
                .withApiVersion(version)
                .build();
    }

    public static IdentityInputsBuilder getIdentityInputs(String input) {
        return new IdentityInputsBuilder.Builder()
                .withNoCatalog(input)
                .build();
    }
}
