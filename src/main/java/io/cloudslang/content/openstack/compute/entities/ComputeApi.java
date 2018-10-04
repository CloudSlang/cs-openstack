package io.cloudslang.content.openstack.compute.entities;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum ComputeApi {
    SERVERS("servers"),
    API("");

    private static final Map<String, String> API_MAP = new HashMap<>();

    static {
        stream(values())
                .forEach(a -> API_MAP.put(a.name().toLowerCase(), a.getValue()));
    }

    private final String value;

    ComputeApi(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return Optional
                .ofNullable(API_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Compute Api: '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(ComputeApi.class))));
    }
}
