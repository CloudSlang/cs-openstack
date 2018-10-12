package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum AutoDiskConfig {
    AUTO("auto"),
    MANUAL("manual");

    private static final Map<String, String> AUTO_DISK_CONFIG_MAP = new HashMap<>();

    static {
        stream(values()).forEach(e -> AUTO_DISK_CONFIG_MAP.put(e.name().toLowerCase(), e.getValue()));
    }

    private final String value;

    AutoDiskConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return Optional
                .ofNullable(AUTO_DISK_CONFIG_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers auto disk config : '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(AutoDiskConfig.class))));
    }
}
