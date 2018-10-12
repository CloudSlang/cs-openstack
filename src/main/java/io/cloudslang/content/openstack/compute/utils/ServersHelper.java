package io.cloudslang.content.openstack.compute.utils;

import io.cloudslang.content.openstack.entities.InputsWrapper;

import java.util.Map;
import java.util.TreeMap;

import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.ACCESS_IP_V4;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.ACCESS_IP_V6;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.ALL_TENANTS;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.AUTO_DISK_CONFIG;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.AVAILABILITY_ZONE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.CHANGES_BEFORE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.CHANGES_SINCE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.CONFIG_DRIVE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.CREATED_AT;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.DELETED;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.DESCRIPTION;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.FLAVOR;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.HOST;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.HOSTNAME;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.IMAGE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.IP;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.IP6;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.KERNEL_ID;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.KEY_NAME;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.LAUNCHED_AT;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.LAUNCH_INDEX;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.LIMIT;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.LOCKED_BY;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.MARKER;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.NAME;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.NODE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.NOT_TAGS;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.NOT_TAGS_ANY;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.POWER_STATE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.PROGRESS;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.PROJECT_ID;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.RAMDISK_ID;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.RESERVATION_ID;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.ROOT_DEVICE_NAME;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.SOFT_DELETED;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.SORT_DIR;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.SORT_KEY;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.STATUS;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.TAGS;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.TAGS_ANY;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.TASK_STATE;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.TERMINATED_AT;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.USER_ID;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.UUID;
import static io.cloudslang.content.openstack.compute.entities.Constants.QueryParams.VM_STATE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ServersHelper {
    public Map<String, String> buildListServersQueryParamsMap(InputsWrapper wrapper) {
        Map<String, String> queryParamsMap = new TreeMap<>();

        setOptionalMapEntry(queryParamsMap, ACCESS_IP_V4, wrapper.getServersInputsBuilder().getAccessIpV4(),
                isNotBlank(wrapper.getServersInputsBuilder().getAccessIpV4()));
        setOptionalMapEntry(queryParamsMap, ACCESS_IP_V6, wrapper.getServersInputsBuilder().getAccessIpV6(),
                isNotBlank(wrapper.getServersInputsBuilder().getAccessIpV6()));
        setOptionalMapEntry(queryParamsMap, AUTO_DISK_CONFIG, wrapper.getServersInputsBuilder().getAutoDiskConfig(),
                isNotBlank(wrapper.getServersInputsBuilder().getAutoDiskConfig()));
        setOptionalMapEntry(queryParamsMap, AVAILABILITY_ZONE, wrapper.getServersInputsBuilder().getAvailabilityZone(),
                isNotBlank(wrapper.getServersInputsBuilder().getAvailabilityZone()));
        setOptionalMapEntry(queryParamsMap, CHANGES_BEFORE, wrapper.getServersInputsBuilder().getChangesBefore(),
                isNotBlank(wrapper.getServersInputsBuilder().getChangesBefore()));
        setOptionalMapEntry(queryParamsMap, CHANGES_SINCE, wrapper.getServersInputsBuilder().getChangesSince(),
                isNotBlank(wrapper.getServersInputsBuilder().getChangesSince()));
        setOptionalMapEntry(queryParamsMap, CONFIG_DRIVE, wrapper.getServersInputsBuilder().getConfigDrive(),
                isNotBlank(wrapper.getServersInputsBuilder().getConfigDrive()));
        setOptionalMapEntry(queryParamsMap, CREATED_AT, wrapper.getServersInputsBuilder().getCreatedAt(),
                isNotBlank(wrapper.getServersInputsBuilder().getCreatedAt()));
        setOptionalMapEntry(queryParamsMap, DESCRIPTION, wrapper.getServersInputsBuilder().getDescription(),
                isNotBlank(wrapper.getServersInputsBuilder().getDescription()));
        setOptionalMapEntry(queryParamsMap, FLAVOR, wrapper.getServersInputsBuilder().getFlavor(),
                isNotBlank(wrapper.getServersInputsBuilder().getFlavor()));
        setOptionalMapEntry(queryParamsMap, HOST, wrapper.getServersInputsBuilder().getHost(),
                isNotBlank(wrapper.getServersInputsBuilder().getHost()));
        setOptionalMapEntry(queryParamsMap, HOSTNAME, wrapper.getServersInputsBuilder().getHostname(),
                isNotBlank(wrapper.getServersInputsBuilder().getHostname()));
        setOptionalMapEntry(queryParamsMap, IMAGE, wrapper.getServersInputsBuilder().getImage(),
                isNotBlank(wrapper.getServersInputsBuilder().getImage()));
        setOptionalMapEntry(queryParamsMap, IP, wrapper.getServersInputsBuilder().getIp(),
                isNotBlank(wrapper.getServersInputsBuilder().getIp()));
        setOptionalMapEntry(queryParamsMap, IP6, wrapper.getServersInputsBuilder().getIp6(),
                isNotBlank(wrapper.getServersInputsBuilder().getIp6()));
        setOptionalMapEntry(queryParamsMap, KERNEL_ID, wrapper.getServersInputsBuilder().getKernelId(),
                isNotBlank(wrapper.getServersInputsBuilder().getKernelId()));
        setOptionalMapEntry(queryParamsMap, KEY_NAME, wrapper.getServersInputsBuilder().getKeyName(),
                isNotBlank(wrapper.getServersInputsBuilder().getKeyName()));
        setOptionalMapEntry(queryParamsMap, LAUNCHED_AT, wrapper.getServersInputsBuilder().getLaunchedAt(),
                isNotBlank(wrapper.getServersInputsBuilder().getLaunchedAt()));
        setOptionalMapEntry(queryParamsMap, LOCKED_BY, wrapper.getServersInputsBuilder().getLockedBy(),
                isNotBlank(wrapper.getServersInputsBuilder().getLockedBy()));
        setOptionalMapEntry(queryParamsMap, MARKER, wrapper.getServersInputsBuilder().getMarker(),
                isNotBlank(wrapper.getServersInputsBuilder().getMarker()));
        setOptionalMapEntry(queryParamsMap, NAME, wrapper.getServersInputsBuilder().getName(),
                isNotBlank(wrapper.getServersInputsBuilder().getName()));
        setOptionalMapEntry(queryParamsMap, NODE, wrapper.getServersInputsBuilder().getNode(),
                isNotBlank(wrapper.getServersInputsBuilder().getNode()));
        setOptionalMapEntry(queryParamsMap, PROJECT_ID, wrapper.getServersInputsBuilder().getProjectId(),
                isNotBlank(wrapper.getServersInputsBuilder().getProjectId()));
        setOptionalMapEntry(queryParamsMap, RAMDISK_ID, wrapper.getServersInputsBuilder().getRamdiskId(),
                isNotBlank(wrapper.getServersInputsBuilder().getRamdiskId()));
        setOptionalMapEntry(queryParamsMap, RESERVATION_ID, wrapper.getServersInputsBuilder().getReservationId(),
                isNotBlank(wrapper.getServersInputsBuilder().getReservationId()));
        setOptionalMapEntry(queryParamsMap, ROOT_DEVICE_NAME, wrapper.getServersInputsBuilder().getRootDeviceName(),
                isNotBlank(wrapper.getServersInputsBuilder().getRootDeviceName()));
        setOptionalMapEntry(queryParamsMap, SOFT_DELETED, wrapper.getServersInputsBuilder().getSoftDeleted(),
                isNotBlank(wrapper.getServersInputsBuilder().getSoftDeleted()));
        setOptionalMapEntry(queryParamsMap, SORT_DIR, wrapper.getServersInputsBuilder().getSortDir(),
                isNotBlank(wrapper.getServersInputsBuilder().getSortDir()));
        setOptionalMapEntry(queryParamsMap, SORT_KEY, wrapper.getServersInputsBuilder().getSortKey(),
                isNotBlank(wrapper.getServersInputsBuilder().getSortKey()));
        setOptionalMapEntry(queryParamsMap, STATUS, wrapper.getServersInputsBuilder().getStatus(),
                isNotBlank(wrapper.getServersInputsBuilder().getStatus()));
        setOptionalMapEntry(queryParamsMap, TASK_STATE, wrapper.getServersInputsBuilder().getTaskState(),
                isNotBlank(wrapper.getServersInputsBuilder().getTaskState()));
        setOptionalMapEntry(queryParamsMap, TERMINATED_AT, wrapper.getServersInputsBuilder().getTerminatedAt(),
                isNotBlank(wrapper.getServersInputsBuilder().getTerminatedAt()));
        setOptionalMapEntry(queryParamsMap, USER_ID, wrapper.getServersInputsBuilder().getUserId(),
                isNotBlank(wrapper.getServersInputsBuilder().getUserId()));
        setOptionalMapEntry(queryParamsMap, UUID, wrapper.getServersInputsBuilder().getUuid(),
                isNotBlank(wrapper.getServersInputsBuilder().getUuid()));
        setOptionalMapEntry(queryParamsMap, VM_STATE, wrapper.getServersInputsBuilder().getVmState(),
                isNotBlank(wrapper.getServersInputsBuilder().getVmState()));
        setOptionalMapEntry(queryParamsMap, NOT_TAGS, wrapper.getServersInputsBuilder().getNotTags(),
                isNotBlank(wrapper.getServersInputsBuilder().getNotTags()));
        setOptionalMapEntry(queryParamsMap, NOT_TAGS_ANY, wrapper.getServersInputsBuilder().getNotTagsAny(),
                isNotBlank(wrapper.getServersInputsBuilder().getNotTagsAny()));
        setOptionalMapEntry(queryParamsMap, TAGS, wrapper.getServersInputsBuilder().getTags(),
                isNotBlank(wrapper.getServersInputsBuilder().getTags()));
        setOptionalMapEntry(queryParamsMap, TAGS_ANY, wrapper.getServersInputsBuilder().getTagsAny(),
                isNotBlank(wrapper.getServersInputsBuilder().getTagsAny()));

        setOptionalMapEntry(queryParamsMap, ALL_TENANTS, valueOf(FALSE), !wrapper.getServersInputsBuilder().getAllTenants());
        setOptionalMapEntry(queryParamsMap, DELETED, valueOf(TRUE), wrapper.getServersInputsBuilder().getDeleted());

        queryParamsMap.put(LAUNCH_INDEX, valueOf(wrapper.getServersInputsBuilder().getLaunchIndex()));
        queryParamsMap.put(LIMIT, valueOf(wrapper.getServersInputsBuilder().getLimit()));
        queryParamsMap.put(POWER_STATE, valueOf(wrapper.getServersInputsBuilder().getPowerState()));
        queryParamsMap.put(PROGRESS, valueOf(wrapper.getServersInputsBuilder().getProgress()));

        return queryParamsMap;
    }

    private static void setOptionalMapEntry(Map<String, String> inputMap, String key, String value, boolean condition) {
        if (condition) {
            inputMap.put(key, value);
        }
    }
}
