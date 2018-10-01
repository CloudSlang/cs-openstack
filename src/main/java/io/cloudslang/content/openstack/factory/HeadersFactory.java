package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class HeadersFactory {
    private HeadersFactory() {
    }

    public static void setHeaders(InputsWrapper wrapper) {
        wrapper.getHttpClientInputs().setContentType(APPLICATION_JSON.getMimeType());

        switch (wrapper.getCommonInputsBuilder().getApi()) {

            default:
                break;
        }
    }
}
