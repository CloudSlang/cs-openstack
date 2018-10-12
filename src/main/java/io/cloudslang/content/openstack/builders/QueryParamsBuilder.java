package io.cloudslang.content.openstack.builders;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_SERVERS;
import static io.cloudslang.content.openstack.compute.factory.ComputeQueryParams.buildServersQueryParamsMap;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.QUESTION_MARK;
import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static io.cloudslang.content.openstack.identity.entities.Constants.QueryParams.NO_CATALOG;
import static io.cloudslang.content.openstack.utils.InputsUtil.getQueryParamsString;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class QueryParamsBuilder {
    private QueryParamsBuilder() {
    }

    public static String buildQueryParams(InputsWrapper wrapper) {
        StringBuilder sb = new StringBuilder(QUESTION_MARK);

        final String action = wrapper.getCommonInputsBuilder().getAction();

        switch (action) {
            case PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION:
                return wrapper.getIdentityInputsBuilder().getNoCatalog() ? EMPTY : sb.append(NO_CATALOG).toString();
            case LIST_SERVERS:
                String rawQueryParamsString = getQueryParamsString(buildServersQueryParamsMap(wrapper));

                return isBlank(rawQueryParamsString) ? EMPTY : sb.append(rawQueryParamsString).toString();
            default:
                return EMPTY;
        }
    }
}
