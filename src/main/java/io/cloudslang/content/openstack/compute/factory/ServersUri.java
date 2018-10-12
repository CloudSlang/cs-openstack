package io.cloudslang.content.openstack.compute.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_SERVERS_DETAILED;
import static io.cloudslang.content.openstack.compute.entities.servers.ServersApi.DETAIL;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class ServersUri {
    private ServersUri() {
    }

    public static String getServersUri(InputsWrapper wrapper) {
        String actions = wrapper.getCommonInputsBuilder().getAction();

        switch (actions) {
            case LIST_SERVERS_DETAILED:
                return DETAIL.getValue();
            default:
                return EMPTY;
        }
    }
}
