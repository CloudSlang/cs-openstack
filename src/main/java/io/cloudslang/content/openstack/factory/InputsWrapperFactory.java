package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ApiInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ServersInputsBuilder;
import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.identity.builders.IdentityInputsBuilder;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

public class InputsWrapperFactory {
    private InputsWrapperFactory() {
    }

    @SafeVarargs
    public static <T> InputsWrapper buildWrapper(HttpClientInputs httpClientInputs, CommonInputsBuilder commonInputsBuilder, T... builders) {
        InputsWrapper.Builder wrapperBuilder = new InputsWrapper.Builder()
                .withHttpClientInputs(httpClientInputs)
                .withCommonInputs(commonInputsBuilder);

        ofNullable(builders)
                .filter(f -> builders.length > 0)
                .ifPresent(buildWith -> stream(builders)
                        .forEach(specificBuilder -> {
                            if (specificBuilder instanceof ApiInputsBuilder) {
                                wrapperBuilder
                                        .withApiInputs((ApiInputsBuilder) specificBuilder);
                            } else if (specificBuilder instanceof IdentityInputsBuilder) {
                                wrapperBuilder
                                        .withIdentityInputs((IdentityInputsBuilder) specificBuilder);
                            } else if (specificBuilder instanceof ServersInputsBuilder) {
                                wrapperBuilder
                                        .withServerInputs((ServersInputsBuilder) specificBuilder);
                            }
                        }));

        return wrapperBuilder.build();
    }
}
