package io.cloudslang.content.openstack.builders;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static org.apache.http.client.methods.HttpPost.METHOD_NAME;

public class PayloadBuilder {
    private PayloadBuilder() {
    }

    public static void buildPayload(InputsWrapper wrapper) {
        if (METHOD_NAME.equals(wrapper.getHttpClientInputs().getMethod())) {
            switch (wrapper.getCommonInputsBuilder().getAction()) {
                default:
                    break;
            }
        }
    }
}
