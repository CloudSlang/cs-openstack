package io.cloudslang.content.openstack.compute.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.DELETE_SERVER;
import static io.vavr.API.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class ServersUri {
    private ServersUri() {
    }

    public static String getServersUri(InputsWrapper wrapper) {
        final String action = wrapper.getCommonInputsBuilder().getAction();

        return Match(action)
                .of(Case($(uri -> DELETE_SERVER.equalsIgnoreCase(action)), () -> wrapper.getServersInputsBuilder().getServerId()),
                        Case($(), () -> EMPTY));
    }
}
