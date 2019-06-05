package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ApiInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ServersInputsBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.identity.builders.IdentityInputsBuilder;

public class InputsWrapperFactory {
    private InputsWrapperFactory() {
    }

    @SafeVarargs
    public static <T> InputsWrapper buildWrapper(HttpClientInputs httpClientInputs, CommonInputsBuilder commonInputsBuilder, T... builders) {
        if (builders != null && builders.length > 0) {
            for (T builder : builders) {
                if (builder instanceof ApiInputsBuilder) {
                    return new InputsWrapper.Builder()
                            .withHttpClientInputs(httpClientInputs)
                            .withCommonInputs(commonInputsBuilder)
                            .withApiInputs((ApiInputsBuilder) builder)
                            .build();
                }

                if (builder instanceof IdentityInputsBuilder) {
                    return new InputsWrapper.Builder()
                            .withHttpClientInputs(httpClientInputs)
                            .withCommonInputs(commonInputsBuilder)
                            .withIdentityInputs((IdentityInputsBuilder) builder)
                            .build();
                }

                if (builder instanceof ServersInputsBuilder) {
                    return new InputsWrapper.Builder()
                            .withHttpClientInputs(httpClientInputs)
                            .withCommonInputs(commonInputsBuilder)
                            .withServerInputs((ServersInputsBuilder) builder)
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
