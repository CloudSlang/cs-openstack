package io.cloudslang.content.openstack.compute.entities.identity;

import io.cloudslang.content.openstack.compute.entities.Api;
import io.cloudslang.content.openstack.compute.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.compute.utils.InputsUtil.getEnumValidValuesString;
import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum AuthenticationMethod {
    PASSWORD("password"),
    TOKEN("token");

    private static final Map<String, String> AUTHENTICATION_METHOD_MAP = new HashMap<>();

    static {
        stream(values())
                .forEach(a -> AUTHENTICATION_METHOD_MAP.put(a.name().toLowerCase(), a.getValue()));
    }

    private final String value;

    AuthenticationMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return Optional
                .ofNullable(AUTHENTICATION_METHOD_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Couchbase Api: '%s'. Valid values: '%s'.",
                        input, getEnumValidValuesString(AuthenticationMethod.class))));
    }
}
