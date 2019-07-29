package io.cloudslang.content.openstack.builders;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import java.util.Optional;

import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_SERVERS;
import static io.cloudslang.content.openstack.compute.factory.ComputeQueryParams.buildServersQueryParamsMap;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.QUESTION_MARK;
import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static io.cloudslang.content.openstack.identity.entities.Constants.QueryParams.NO_CATALOG;
import static io.cloudslang.content.openstack.utils.InputsUtil.getQueryParamsUri;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class QueryParamsBuilder {
    private QueryParamsBuilder() {
    }

    public static String buildQueryParams(InputsWrapper wrapper) {
        StringBuilder sb = new StringBuilder(QUESTION_MARK);

        final String action = wrapper.getCommonInputsBuilder().getAction();

        switch (action) {
            case PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION:
                return Optional
                        .of(wrapper.getIdentityInputsBuilder().getNoCatalog())
                        .map(s -> EMPTY)
                        .orElse(sb.append(NO_CATALOG).toString());
            case LIST_SERVERS:
                String rawQueryParamsString = getQueryParamsUri(buildServersQueryParamsMap(wrapper));

                return Optional
                        .ofNullable(rawQueryParamsString)
                        .map(s -> sb.append(rawQueryParamsString).toString())
                        .orElse(EMPTY);
            default:
                return EMPTY;
        }
    }
}
