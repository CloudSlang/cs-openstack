package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputsBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;

public class InputsWrapperFactory {
    private InputsWrapperFactory() {
    }

    @SafeVarargs
    public static <T> InputsWrapper buildWrapper(HttpClientInputs httpClientInputs, CommonInputsBuilder commonInputsBuilder, T... builders) {
        if (builders != null && builders.length > 0) {
            for (T builder : builders) {
                if (builder instanceof ApiInputsBuilder) {
                    return new InputsWrapper.Builder()
                            .withApiInputs((ApiInputsBuilder) builder)
                            .withCommonInputs(commonInputsBuilder)
                            .withHttpClientInputs(httpClientInputs)
                            .build();
                }
            }
        }

        return new InputsWrapper.Builder()
                .withCommonInputs(commonInputsBuilder)
                .withHttpClientInputs(httpClientInputs)
                .build();
    }
}
