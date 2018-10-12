package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum PowerState {
    NOSTATE(0),
    RUNNING(1),
    PAUSED(3),
    SHUTDOWN(4),
    CRASHED(6),
    SUSPENDED(7);

    private static final Map<String, Integer> POWER_STATE_MAP = new HashMap<>();

    static {
        stream(values()).forEach(e -> POWER_STATE_MAP.put(e.name().toLowerCase(), e.getValue()));
    }

    private final Integer value;

    PowerState(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static int fromString(String input) throws OpenstackException {
        return Optional
                .ofNullable(POWER_STATE_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers power state config : '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(PowerState.class))));
    }
}
