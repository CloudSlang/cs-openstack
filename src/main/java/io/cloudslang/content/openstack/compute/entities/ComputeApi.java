package io.cloudslang.content.openstack.compute.entities;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Map;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public enum ComputeApi {
    SERVERS("servers"),
    API("");

    private static final Map<String, String> API_MAP = stream(values())
            .collect(toMap(e -> e.name().toLowerCase(), ComputeApi::getValue));

    private final String value;

    ComputeApi(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return ofNullable(API_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Compute Api: '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(ComputeApi.class))));
    }
}
