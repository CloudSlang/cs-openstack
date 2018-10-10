package io.cloudslang.content.openstack.compute.entities;

public class Inputs {

    private Inputs() {
    }

    public static class Api {
        public static final String API_VERSION = "apiVersion";
    }

    public static class Servers {
        public static final String ACCESS_IP_V4 = "accessIpV4";
        public static final String ACCESS_IP_V6 = "accessIpV6 ";
        public static final String ALL_TENANTS = "allTenants";
        public static final String AUTO_DISK_CONFIG = "autoDiskConfig";
        public static final String AVAILABILITY_ZONE = "availabilityZone";
        public static final String CHANGES_SINCE = "changesSince";
        public static final String CONFIG_DRIVE = "configDrive";
        public static final String CREATED_AT = "createdAt";
        public static final String DELETED = "deleted";
        public static final String DESCRIPTION = "description";
        public static final String FLAVOR = "flavor";
        public static final String HOST = "host";
        public static final String HOSTNAME = "hostname";
        public static final String IMAGE = "image";
        public static final String IP = "ip";
        public static final String IP6 = "ip6";
        public static final String KERNEL_ID = "kernelId";
        public static final String KEY_NAME = "keyName";
        public static final String LAUNCH_INDEX = "launchIndex";
        public static final String LAUNCHED_AT = "launchedAt";
        public static final String LIMIT = "limit";
        public static final String LOCKED_BY = "lockedBy";
        public static final String MARKER = "marker";
        public static final String NAME = "name";
        public static final String NODE = "node";
        public static final String POWER_STATE = "powerState";
        public static final String PROGRESS = "progress";
        public static final String PROJECT_ID = "projectId";
        public static final String RAMDISK_ID = "ramdiskId";
        public static final String RESERVATION_ID = "reservationId";
        public static final String ROOT_DEVICE_NAME = "rootDeviceName";
        public static final String SOFT_DELETED = "softDeleted";
        public static final String SORT_DIR = "sortDir";
        public static final String SORT_KEY = "sortKey";
        public static final String STATUS = "status";
        public static final String TASK_STATE = "taskState";
        public static final String TERMINATED_AT = "terminatedAt";
        public static final String USER_ID = "userId";
        public static final String UUID = "uuid";
        public static final String VM_STATE = "vmState";
        public static final String NOT_TAGS = "notTags";
        public static final String NOT_TAGS_ANY = "notTagsAny";
        public static final String TAGS = "tags";
        public static final String TAGS_ANY = "tagsAny";
        public static final String CHANGES_BEFORE = "changesBefore";
    }
}
