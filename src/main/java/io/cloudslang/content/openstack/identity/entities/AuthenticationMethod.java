package io.cloudslang.content.openstack.identity.entities;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
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
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack authentication method: '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(AuthenticationMethod.class))));
    }
}
