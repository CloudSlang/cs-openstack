package io.cloudslang.content.openstack.compute.factory;

import io.cloudslang.content.openstack.compute.utils.ServersHelper;
import io.cloudslang.content.openstack.entities.InputsWrapper;

import java.util.Map;

import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_SERVERS;

public class ComputeQueryParams {
    private ComputeQueryParams() {
    }

    public static Map<String, String> buildServersQueryParamsMap(InputsWrapper wrapper) {
        String actions = wrapper.getCommonInputsBuilder().getAction();

        switch (actions) {
            case LIST_SERVERS:
                return new ServersHelper().buildListServersQueryParamsMap(wrapper);
            default:
                return null;
        }
    }
}
