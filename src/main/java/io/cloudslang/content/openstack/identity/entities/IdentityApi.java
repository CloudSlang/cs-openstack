package io.cloudslang.content.openstack.identity.entities;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.utils.InputsUtil.getEnumValidValuesString;
import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum IdentityApi {
    AUTH("auth"),
    TOKENS("tokens");

    private static final Map<String, String> IDENTITY_API_MAP = new HashMap<>();

    static {
        stream(values())
                .forEach(a -> IDENTITY_API_MAP.put(a.name().toLowerCase(), a.getValue()));
    }

    private final String value;

    IdentityApi(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return Optional
                .ofNullable(IDENTITY_API_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Identity Api: '%s'. Valid values: '%s'.",
                        input, getEnumValidValuesString(IdentityApi.class))));
    }
}
