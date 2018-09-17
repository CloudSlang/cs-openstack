package io.cloudslang.content.openstack.compute.entities;

import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.compute.utils.InputsUtil.getEnumValidValuesString;
import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum Api {
    SERVERS("servers"),
    API("");

    private static final Map<String, String> API_MAP = new HashMap<>();

    static {
        stream(values())
                .forEach(a -> API_MAP.put(a.name().toLowerCase(), a.getValue()));
    }

    private final String value;

    Api(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(String input) throws OpenstackException {
        return Optional
                .ofNullable(API_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Couchbase Api: '%s'. Valid values: '%s'.",
                        input, getEnumValidValuesString(Api.class))));
    }
}
