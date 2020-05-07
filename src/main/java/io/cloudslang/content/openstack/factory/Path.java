package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.openstack.compute.entities.ComputeApi;
import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.Constants.Actions.LIST_SERVERS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.SERVERS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Uri.COMPUTE_URI;
import static io.cloudslang.content.openstack.compute.entities.Constants.Values.COMPUTE_PORT;
import static io.cloudslang.content.openstack.compute.factory.ComputeQueryParams.buildServersQueryParamsMap;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.identity.entities.Constants.Actions.PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION;
import static io.cloudslang.content.openstack.identity.entities.Constants.Api.IDENTITY;
import static io.cloudslang.content.openstack.identity.entities.Constants.QueryParams.NO_CATALOG;
import static io.cloudslang.content.openstack.identity.entities.Constants.Values.IDENTITY_PORT;
import static io.cloudslang.content.openstack.utils.InputsUtil.getQueryParamsUri;
import static io.vavr.API.*;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.join;

public class Path {
    private Path() {
    }

    public static String getPath(InputsWrapper wrapper) {
        final String api = wrapper.getCommonInputsBuilder().getApi();
        final String baseVersion = wrapper.getCommonInputsBuilder().getVersion();

        return Match(api)
                .of(
                        Case($(qcd -> API.equalsIgnoreCase(api)), () -> join(new String[]{COMPUTE_PORT, COMPUTE_URI, baseVersion}, SLASH)),
                        Case($(qcd -> IDENTITY.equalsIgnoreCase(api)), () -> join(new String[]{IDENTITY_PORT, baseVersion}, SLASH)),
                        Case($(qcd -> SERVERS.equalsIgnoreCase(api)), () -> join(new String[]{COMPUTE_PORT, COMPUTE_URI, baseVersion, ComputeApi.SERVERS.getValue()}, SLASH)),
                        Case($(), () -> EMPTY)
                );
    }
}
