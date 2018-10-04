package io.cloudslang.content.openstack.builders;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static io.cloudslang.content.openstack.identity.entities.Constants.QueryParams.NO_CATALOG;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class QueryParamsBuilder {
    private QueryParamsBuilder() {
    }

    public static String buildQueryParams(InputsWrapper wrapper) {
        StringBuilder sb = new StringBuilder("?");

        switch (wrapper.getCommonInputsBuilder().getAction()) {
            case PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION:
                return wrapper.getIdentityInputsBuilder().getNoCatalog() ? EMPTY : sb.append(NO_CATALOG).toString();
            default:
                return EMPTY;
        }
    }
}
