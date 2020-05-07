package io.cloudslang.content.openstack.compute.factory;

import io.cloudslang.content.openstack.compute.utils.ServersHelper;
import io.cloudslang.content.openstack.entities.InputsWrapper;

import java.util.Map;

import static io.cloudslang.content.openstack.compute.entities.ComputeApi.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.GET_API_VERSION_DETAILS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_SERVERS;
import static io.vavr.API.*;

public class ComputeQueryParams {
    private ComputeQueryParams() {
    }

    public static Map<String, String> buildServersQueryParamsMap(InputsWrapper wrapper) {
        final String action = wrapper.getCommonInputsBuilder().getAction();

        return Match(action)
                .of(Case($(uri -> LIST_SERVERS.equalsIgnoreCase(action)), () -> new ServersHelper().buildListServersQueryParamsMap(wrapper)),
                        Case($(), () -> null));
    }
}
