package io.cloudslang.content.openstack.compute;

import io.cloudslang.content.openstack.compute.builders.CommonInputs;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputs;
import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;
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

    public static CommonInputs getCommonInputs(String endpoint, String api, String version, String action) {
        return new CommonInputs.Builder()
                .withEndpoint(endpoint)
                .withApi(api)
                .withVersion(version)
                .withAction(action)
                .build();
    }

    public static ApiInputs getApiInputs(String version) {
        return new ApiInputs.Builder()
                .withApiVersion(version)
                .build();
    }
}
