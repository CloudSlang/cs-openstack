package io.cloudslang.content.openstack.factory;

import io.cloudslang.content.openstack.entities.InputsWrapper;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static io.cloudslang.content.openstack.compute.entities.Constants.Api.API;
import static io.cloudslang.content.openstack.entities.Constants.Headers.REQUEST_ID_HEADER_PREFIX;
import static io.cloudslang.content.openstack.entities.Constants.Headers.X_AUTH_TOKEN;
import static io.cloudslang.content.openstack.entities.Constants.Headers.X_OPENSTACK_REQUEST_ID;
import static io.cloudslang.content.openstack.entities.Constants.Values.THRESHOLD_VERSION_FOR_REQUEST_UUID_PRESENCE;
import static io.cloudslang.content.openstack.validators.Validators.isInputGreaterOrEqualThanThreshold;
import static java.lang.String.join;
import static java.util.UUID.randomUUID;

public class Headers {
    private Headers() {
    }

    public static void setHeaders(InputsWrapper wrapper) {
        Set<Header> headersSet = new HashSet<>();

        if (isInputGreaterOrEqualThanThreshold(wrapper.getCommonInputsBuilder().getVersion(), THRESHOLD_VERSION_FOR_REQUEST_UUID_PRESENCE)) {
            headersSet.add(new BasicHeader(X_OPENSTACK_REQUEST_ID, join(REQUEST_ID_HEADER_PREFIX + randomUUID().toString())));
        }

        switch (wrapper.getCommonInputsBuilder().getApi()) {
            case API:
                break;
            default:
                headersSet.add(new BasicHeader(X_AUTH_TOKEN, wrapper.getCommonInputsBuilder().getToken()));
                break;
        }

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        headersSet.stream()
                .filter(Objects::nonNull)
                .forEach(h -> sj.add(h.toString()));

        wrapper.getHttpClientInputs().setHeaders(sj.toString());
    }
}
