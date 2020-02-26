package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Map;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public enum AutoDiskConfig {
    AUTO("auto"),
    MANUAL("manual");

    private static final Map<String, String> AUTO_DISK_CONFIG_MAP = stream(values())
            .collect(toMap(v -> v.name().toLowerCase(), AutoDiskConfig::getValue));

    private final String value;

    AutoDiskConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return ofNullable(AUTO_DISK_CONFIG_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers auto disk config : '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(AutoDiskConfig.class))));
    }
}
