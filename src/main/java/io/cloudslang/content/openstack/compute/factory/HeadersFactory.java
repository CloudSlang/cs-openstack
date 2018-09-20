package io.cloudslang.content.openstack.compute.factory;

import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;

public class HeadersFactory {
    private HeadersFactory() {
    }

    public static void setHeaders(InputsWrapper wrapper) {
        switch (wrapper.getCommonInputsBuilder().getApi()) {

            default:
                break;
        }
    }
}
