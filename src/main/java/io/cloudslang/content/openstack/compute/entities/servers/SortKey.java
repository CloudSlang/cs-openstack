package io.cloudslang.content.openstack.compute.entities.servers;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.cloudslang.content.openstack.utils.InputsUtil.buildErrorMessage;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.isBlank;

public enum SortKey {
    ACCESS_IP_V4("access_ip_v4"),
    ACCESS_IP_V6("ACCESS_IP_V6"),
    AUTO_DISK_CONFIG("auto_disk_config"),
    AVAILABILITY_ZONE("availability_zone"),
    CONFIG_DRIVE("config_drive"),
    CREATED_AT("created_at"),
    DISPLAY_DESCRIPTION("display_description"),
    DISPLAY_NAME("display_name"),
    HOST("host"),
    HOSTNAME("hostname"),
    IMAGE_REF("image_ref"),
    INSTANCE_TYPE_ID("instance_type_id"),
    KERNEL_ID("kernel_id"),
    KEY_NAME("key_name"),
    LAUNCH_INDEX("launch_index"),
    LAUNCHED_AT("launched_at"),
    LOCKED_BY("locked_by"),
    NODE("node"),
    POWER_STATE("power_state"),
    PROGRESS("progress"),
    PROJECT_ID("project_id"),
    RAMDISK_ID("ramdisk_id"),
    ROOT_DEVICE_NAME("root_device_name"),
    TASK_STATE("task_state"),
    TERMINATED_AT("terminated_at"),
    UPDATED_AT("updated_at"),
    USER_ID("user_id"),
    UUID("uuid"),
    VM_STATE("vm_state");

    private static final Map<String, String> SORT_KEY_MAP = new HashMap<>();

    static {
        stream(values()).forEach(e -> SORT_KEY_MAP.put(e.name().toLowerCase(), e.getValue()));
    }

    private final String value;

    SortKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String input) throws OpenstackException {
        return isBlank(input) ? CREATED_AT.getValue() :
                Optional
                        .ofNullable(SORT_KEY_MAP.get(input))
                        .orElseThrow(() -> new OpenstackException(format("Invalid Openstack Servers locked by config : '%s'. Valid values: '%s'.",
                                input, buildErrorMessage(SortKey.class))));
    }
}
