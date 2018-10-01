package io.cloudslang.content.openstack.compute.factory.api;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.ComputeApi.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.GET_API_VERSION_DETAILS;

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
