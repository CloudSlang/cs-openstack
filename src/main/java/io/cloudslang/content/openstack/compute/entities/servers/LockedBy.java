package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Map;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public enum LockedBy {
    ADMIN("admin"),
    OWNER("owner");

    private static final Map<String, String> LOCKED_BY_MAP = stream(values())
            .collect(toMap(v -> v.name().toLowerCase(), LockedBy::getValue));

    private final String value;

    LockedBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return ofNullable(LOCKED_BY_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers locked by config : '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(LockedBy.class))));
    }
}
