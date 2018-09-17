package io.cloudslang.content.openstack.compute.builders;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputs;
import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;
import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;

import static java.lang.String.format;

public class InputsWrapperBuilder {
    private InputsWrapperBuilder() {
    }

    @SafeVarargs
    public static <T> InputsWrapper buildWrapper(HttpClientInputs httpClientInputs, CommonInputs commonInputs, T... builders) throws OpenstackException {
        InputsWrapper wrapper = new InputsWrapper.Builder()
                .withHttpClientInputs(httpClientInputs)
                .withCommonInputs(commonInputs)
                .build();

        if (builders != null && builders.length > 0) {
            for (T builder : builders) {
                if (builder instanceof ApiInputs) {
                    wrapper.setApiInputs((ApiInputs) builder);
                } else {
                    throw new OpenstackException(format("Unknown builder type: %s", builder.toString()));
                }
            }
        }

        return wrapper;
    }
}
