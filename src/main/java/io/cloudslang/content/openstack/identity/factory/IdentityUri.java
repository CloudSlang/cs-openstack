package io.cloudslang.content.openstack.identity.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static io.cloudslang.content.openstack.identity.entities.IdentityApi.AUTH;
import static io.cloudslang.content.openstack.identity.entities.IdentityApi.TOKENS;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;

public class IdentityUri {
    private IdentityUri() {
    }

    public static String getIdentityUri(InputsWrapper wrapper) {
        String actions = wrapper.getCommonInputsBuilder().getAction();

        switch (actions) {
            case PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION:
                return appendIfMissing(AUTH.getValue(), SLASH) + TOKENS.getValue();
            default:
                return EMPTY;
        }
    }
}
