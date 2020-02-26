package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Set;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

public enum VmState {
    ACTIVE,
    BUILDING,
    DELETED,
    ERROR,
    PAUSED,
    RESCUED,
    RESIZED,
    SHELVED,
    SHELVED_OFFLOADED,
    SOFT_DELETED,
    STOPPED,
    SUSPENDED;

    private static final Set<String> VM_STATE_SET = stream(values())
            .map(Enum::name)
            .collect(toSet());

    public static String fromString(String input) throws OpenstackException {
        //noinspection OptionalGetWithoutIsPresent
        return isBlank(input) ? EMPTY :
                of(VM_STATE_SET.stream()
                        .filter(f -> f.equalsIgnoreCase(input))
                        .findFirst()
                        .get())
                        .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers VM state config : '%s'. Valid values: '%s'.",
                                input, buildErrorMessage(LockedBy.class))));
    }
}
