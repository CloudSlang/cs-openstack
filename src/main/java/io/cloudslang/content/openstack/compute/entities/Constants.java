package io.cloudslang.content.openstack.compute.entities;

public class Constants {
    private Constants() {
    }

    public static class Actions {
        public static final String GET_API_VERSION_DETAILS = "GetApiVersionDetails";
        public static final String LIST_ALL_MAJOR_VERSIONS = "ListAllMajorVersions";
        public static final String LIST_SERVERS = "ListServers";
        public static final String LIST_SERVERS_DETAILED = "ListServersDetailed";
    }

    public static class Api {
        public static final String API = "api";
        public static final String SERVERS = "servers";
    }

    public static class QueryParams {
        public static final String ACCESS_IP_V4 = "access_ip_v4";
        public static final String ACCESS_IP_V6 = "access_ip_v6 ";
        public static final String ALL_TENANTS = "all_tenants";
        public static final String AUTO_DISK_CONFIG = "auto_disk_config";
        public static final String AVAILABILITY_ZONE = "availability_zone";
        public static final String CHANGES_SINCE = "changes-since";
        public static final String CONFIG_DRIVE = "config_drive";
        public static final String CREATED_AT = "created_at";
        public static final String DELETED = "deleted";
        public static final String DESCRIPTION = "description";
        public static final String FLAVOR = "flavor";
        public static final String HOST = "host";
        public static final String HOSTNAME = "hostname";
        public static final String IMAGE = "image";
        public static final String IP = "ip";
        public static final String IP6 = "ip6";
        public static final String KERNEL_ID = "kernel_id";
        public static final String KEY_NAME = "key_name";
        public static final String LAUNCH_INDEX = "launch_index";
        public static final String LAUNCHED_AT = "launched_at";
        public static final String LIMIT = "limit";
        public static final String LOCKED_BY = "locked_by";
        public static final String MARKER = "marker";
        public static final String NAME = "name";
        public static final String NODE = "node";
        public static final String POWER_STATE = "power_state";
        public static final String PROGRESS = "progress";
        public static final String PROJECT_ID = "project_id";
        public static final String RAMDISK_ID = "ramdisk_id";
        public static final String RESERVATION_ID = "reservation_id";
        public static final String ROOT_DEVICE_NAME = "root_device_name";
        public static final String SOFT_DELETED = "soft_deleted";
        public static final String SORT_DIR = "sort_dir";
        public static final String SORT_KEY = "sort_key";
        public static final String STATUS = "status";
        public static final String TASK_STATE = "task_state";
        public static final String TERMINATED_AT = "terminated_at";
        public static final String USER_ID = "user_id";
        public static final String UUID = "uuid";
        public static final String VM_STATE = "vm_state";
        public static final String NOT_TAGS = "not-tags";
        public static final String NOT_TAGS_ANY = "not-tags-any";
        public static final String TAGS = "tags";
        public static final String TAGS_ANY = "tags-any";
        public static final String CHANGES_BEFORE = "changes-before";
    }

    public static class Uri {
        public static final String COMPUTE_URI = "compute";
    }

    public static class Values {
        public static final String COMPUTE_PORT = "8774";
    }

    public static class Versions {
        public static final String DEFAULT_COMPUTE_VERSION = "v2.0";
    }
}
