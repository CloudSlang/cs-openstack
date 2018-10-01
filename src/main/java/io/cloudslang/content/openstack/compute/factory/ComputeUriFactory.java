package io.cloudslang.content.openstack.compute.factory;

import io.cloudslang.content.openstack.exceptions.OpenstackException;
import io.cloudslang.content.openstack.entities.InputsWrapper;

import static io.cloudslang.content.openstack.compute.entities.Constants.ComputeApis.API;
import static io.cloudslang.content.openstack.compute.entities.Constants.ComputeApis.SERVERS;
import static io.cloudslang.content.openstack.compute.entities.Constants.Uri.COMPUTE_URI;
import static io.cloudslang.content.openstack.compute.factory.api.ApiUrifactory.getApiUri;
import static io.cloudslang.content.openstack.entities.Constants.Miscellaneous.SLASH;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;

public class ComputeUriFactory {
    private ComputeUriFactory() {
    }

    public static String getUri(InputsWrapper wrapper) throws OpenstackException {
        String api = wrapper.getCommonInputsBuilder().getApi();

        switch (api) {
            case API:
                return appendIfMissing(COMPUTE_URI, SLASH) + wrapper.getCommonInputsBuilder().getVersion() + getApiUri(wrapper);
            case SERVERS:
                return appendIfMissing(COMPUTE_URI + SLASH + SERVERS, SLASH) + wrapper.getCommonInputsBuilder().getVersion();
            default:
                throw new OpenstackException(format("Unknown Compute API: %s", api));
        }
    }
}
