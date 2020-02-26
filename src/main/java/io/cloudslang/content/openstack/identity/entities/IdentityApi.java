package io.cloudslang.content.openstack.identity.entities;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Map;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public enum IdentityApi {
    AUTH("auth"),
    CATALOG("catalog"),
    DOMAINS("domains"),
    PROJECTS("projects"),
    SYSTEM("system"),
    TOKENS("tokens");

    private static final Map<String, String> IDENTITY_API_MAP = stream(values())
            .collect(toMap(v -> v.name().toLowerCase(), IdentityApi::getValue));

    private final String value;

    IdentityApi(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return ofNullable(IDENTITY_API_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Identity Api: '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(IdentityApi.class))));
    }
}
