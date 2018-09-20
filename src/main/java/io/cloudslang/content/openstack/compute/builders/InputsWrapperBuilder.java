package io.cloudslang.content.openstack.compute.builders;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputsBuilder;
import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;
import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;

import static java.lang.String.format;

public class InputsWrapperBuilder {
    private InputsWrapperBuilder() {
    }

    @SafeVarargs
    public static <T> InputsWrapper buildWrapper(HttpClientInputs httpClientInputs, CommonInputsBuilder commonInputsBuilder, T... builders) throws OpenstackException {
        InputsWrapper wrapper = new InputsWrapper.Builder()
                .withHttpClientInputs(httpClientInputs)
                .withCommonInputs(commonInputsBuilder)
                .build();

        if (builders != null && builders.length > 0) {
            for (T builder : builders) {
                if (builder instanceof ApiInputsBuilder) {
                    wrapper.setApiInputsBuilder((ApiInputsBuilder) builder);
                } else {
                    throw new OpenstackException(format("Unknown builder type: %s", builder.toString()));
                }
            }
        }

        return wrapper;
    }
}
