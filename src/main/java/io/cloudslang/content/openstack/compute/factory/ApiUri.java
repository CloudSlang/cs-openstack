package io.cloudslang.content.openstack.compute.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.ComputeApi.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.GET_API_VERSION_DETAILS;
import static io.vavr.API.*;

public class ApiUri {
    private ApiUri() {
    }

    public static String getApiUri(InputsWrapper wrapper) {
        final String action = wrapper.getCommonInputsBuilder().getAction();

        return Match(action)
                .of(Case($(uri -> GET_API_VERSION_DETAILS.equalsIgnoreCase(action)), () -> wrapper.getApiInputsBuilder().getApiVersion()),
                        Case($(), API::getValue));
    }
}
