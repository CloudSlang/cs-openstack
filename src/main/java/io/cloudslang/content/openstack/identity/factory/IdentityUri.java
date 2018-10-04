package io.cloudslang.content.openstack.identity.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.GET_AVAILABLE_PROJECT_SCOPES;
import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.GET_SERVICE_CATALOG;
import static io.cloudslang.content.openstack.identity.entities.IdentityApi.AUTH;
import static io.cloudslang.content.openstack.identity.entities.IdentityApi.CATALOG;
import static io.cloudslang.content.openstack.identity.entities.IdentityApi.PROJECTS;
import static io.cloudslang.content.openstack.identity.entities.IdentityApi.TOKENS;
import static org.apache.commons.lang3.StringUtils.join;

public class IdentityUri {
    private IdentityUri() {
    }

    public static String getIdentityUri(InputsWrapper wrapper) {
        String actions = wrapper.getCommonInputsBuilder().getAction();

        switch (actions) {
            case GET_AVAILABLE_PROJECT_SCOPES:
                return join(new String[]{AUTH.getValue(), PROJECTS.getValue()}, SLASH);
            case GET_SERVICE_CATALOG:
                return join(new String[]{AUTH.getValue(), CATALOG.getValue()}, SLASH);
            default:
                return join(new String[]{AUTH.getValue(), TOKENS.getValue()}, SLASH);
        }
    }
}
