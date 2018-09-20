package io.cloudslang.content.openstack.compute.factory.api;

import io.cloudslang.content.openstack.compute.wrappers.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.Api.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.Api.GET_API_VERSION_DETAILS;

public class ApiUrifactory {
    private ApiUrifactory() {
    }

    public static String getApiUri(InputsWrapper wrapper) {
        String actions = wrapper.getCommonInputsBuilder().getAction();

        switch (actions) {
            case GET_API_VERSION_DETAILS:
                return wrapper.getApiInputsBuilder().getApiVersion();
            default:
                return API.getValue();
        }
    }
}
