package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Map;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

public enum ServersApi {
    ACTION("action"),
    DIAGNOSTICS("diagnostics"),
    DETAIL("detail"),
    IPS("ips"),
    METADATA("metadata"),
    OS_FLAVOR_ACCESS("os-flavor-access"),
    OS_INSTANCE_ACTIONS("os-instance-actions"),
    OS_INTERFACE("os-interface"),
    OS_SECURITY_GROUPS("os-security-groups"),
    OS_SERVER_PASSWORD("os-server-password"),
    OS_VOLUME_ATTACHMENTS("os-volume_attachments"),
    REMOTE_CONSOLES("remote-consoles");

    private static final Map<String, String> SERVERS_API_MAP = stream(values())
            .collect(toMap(v -> v.name().toLowerCase(), ServersApi::getValue));

    private final String value;

    ServersApi(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return ofNullable(SERVERS_API_MAP.get(input))
                .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers Api: '%s'. Valid values: '%s'.",
                        input, buildErrorMessage(ServersApi.class))));
    }
}
