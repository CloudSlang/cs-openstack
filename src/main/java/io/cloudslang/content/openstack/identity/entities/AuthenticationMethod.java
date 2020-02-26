package io.cloudslang.content.openstack.identity.entities;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Map;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public enum AuthenticationMethod {
    PASSWORD("password"),
    TOKEN("token");

    private static final Map<String, String> AUTHENTICATION_METHOD_MAP = stream(values())
            .collect(toMap(v -> v.name().toLowerCase(), AuthenticationMethod::getValue));

    private final String value;

    AuthenticationMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return ofNullable(AUTHENTICATION_METHOD_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack authentication method: '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(AuthenticationMethod.class))));
    }
}
