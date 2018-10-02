package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;
import io.cloudslang.content.openstack.exceptions.OpenstackException;

import static io.cloudslang.content.openstack.compute.entities.Constants.Api.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.Api.SERVERS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Uri.COMPUTE_URI;
import static io.cloudslang.content.openstack.compute.factory.api.ApiUrifactory.getApiUri;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.COLON;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.identity.entities.Constants.Api.IDENTITY;
import static io.cloudslang.content.openstack.identity.entities.Constants.Values.IDENTITY_PORT;
import static io.cloudslang.content.openstack.identity.factory.IdentityUri.getIdentityUri;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;

public class UriFactory {
    private UriFactory() {
    }

    public static String getUri(InputsWrapper wrapper) throws OpenstackException {
        String api = wrapper.getCommonInputsBuilder().getApi();

        switch (api) {
            case API:
                return appendIfMissing(COMPUTE_URI, SLASH) + wrapper.getCommonInputsBuilder().getVersion() + getApiUri(wrapper);
            case IDENTITY:
                return appendIfMissing(COLON + IDENTITY_PORT, SLASH)
                        + appendIfMissing(wrapper.getCommonInputsBuilder().getVersion(), SLASH) + getIdentityUri(wrapper);
            case SERVERS:
                return appendIfMissing(COMPUTE_URI + SLASH + SERVERS, SLASH) + wrapper.getCommonInputsBuilder().getVersion();
            default:
                throw new OpenstackException(format("Unknown Compute API: %s", api));
        }
    }
}
