package io.cloudslang.content.openstack.compute;

import io.cloudslang.content.openstack.compute.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputsBuilder;
import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;
import org.junit.rules.ExpectedException;

import static java.util.Arrays.copyOf;

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

    @SafeVarargs
    public static <E> E[] newArray(int length, E... array) {
        return copyOf(array, length);
    }
}
