package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.openstack.compute.entities.ComputeApi;
import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.Constants.Api.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.SERVERS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Uri.COMPUTE_URI;
import static io.cloudslang.content.openstack.compute.entities.Constants.Values.COMPUTE_PORT;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.identity.entities.Constants.Api.IDENTITY;
import static io.cloudslang.content.openstack.identity.entities.Constants.Values.IDENTITY_PORT;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.join;

public class Prefix {
    private Prefix() {
    }

    public static String getPrefix(InputsWrapper wrapper) {
        String api = wrapper.getCommonInputsBuilder().getApi();
        String baseVersion = wrapper.getCommonInputsBuilder().getVersion();

        switch (api) {
            case API:
                return join(new String[]{COMPUTE_PORT, COMPUTE_URI, baseVersion}, SLASH);
            case IDENTITY:
                return join(new String[]{IDENTITY_PORT, baseVersion}, SLASH);
            case SERVERS:
                return join(new String[]{COMPUTE_PORT, COMPUTE_URI, baseVersion, ComputeApi.SERVERS.getValue()}, SLASH);
            default:
                return EMPTY;
        }
    }
}
