package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

public enum Status {
    ACTIVE("active"),
    BUILD("build"),
    DELETED("deleted"),
    ERROR("error"),
    HARD_REBOOT("hard_reboot"),
    MIGRATING("migrating"),
    PASSWORD("password"),
    PAUSED("paused"),
    REBOOT("reboot"),
    REBUILD("rebuild"),
    RESCUE("rescue"),
    RESIZE("resize"),
    REVERT_RESIZE("revert_resize"),
    SHELVED("shelved"),
    SHELVED_OFFLOADED("shelved_offloaded"),
    SHUTOFF("shutoff"),
    SOFT_DELETED("soft_deleted"),
    SUSPENDED("suspended"),
    UNKNOWN("unknown"),
    VERIFY_RESIZE("verify_resize");

    private static final Map<String, String> STATUS_MAP = new HashMap<>();

    static {
        stream(values()).forEach(e -> STATUS_MAP.put(e.name().toLowerCase(), e.getValue()));
    }

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return isBlank(input) ? EMPTY : Optional
                .ofNullable(STATUS_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers status config : '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(Status.class))));
    }
}
