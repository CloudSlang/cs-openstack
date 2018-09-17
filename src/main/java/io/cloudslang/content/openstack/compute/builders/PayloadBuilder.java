package io.cloudslang.content.openstack.compute.builders;

import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;

import static org.apache.http.client.methods.HttpPost.METHOD_NAME;

public class PayloadBuilder {
    private PayloadBuilder() {
    }

    public static void buildPayload(InputsWrapper wrapper) {
        if (METHOD_NAME.equals(wrapper.getHttpClientInputs().getMethod())) {
            switch (wrapper.getCommonInputs().getAction()) {
                default:
                    break;
            }
        }
    }
}
