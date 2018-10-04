package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.exceptions.OpenstackException;

import static io.cloudslang.content.openstack.compute.entities.Constants.Api.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.SERVERS;
import static io.cloudslang.content.openstack.compute.factory.ApiUrifactory.getApiUri;
import static io.cloudslang.content.openstack.compute.factory.ServersUriFactory.getServersUri;
import static io.cloudslang.content.openstack.identity.entities.Constants.Api.IDENTITY;
import static io.cloudslang.content.openstack.identity.factory.IdentityUri.getIdentityUri;
import static java.lang.String.format;

public class UriFactory {
    private UriFactory() {
    }

    public static String getUri(InputsWrapper wrapper) throws OpenstackException {
        String api = wrapper.getCommonInputsBuilder().getApi();

        switch (api) {
            case API:
                return getApiUri(wrapper);
            case IDENTITY:
                return getIdentityUri(wrapper);
            case SERVERS:
                return getServersUri(wrapper);
            default:
                throw new OpenstackException(format("Unknown Compute API: %s", api));
        }
    }
}
