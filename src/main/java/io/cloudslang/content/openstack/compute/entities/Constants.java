package io.cloudslang.content.openstack.compute.entities;

public class Constants {
    private Constants() {
    }

    public static class Actions {
        public static final String GET_API_VERSION_DETAILS = "GetApiVersionDetails";
        public static final String LIST_ALL_MAJOR_VERSIONS = "ListAllMajorVersions";
    }

    public static class ComputeApis {
        public static final String API = "api";
        public static final String SERVERS = "servers";
    }

    public static class Uri {
        public static final String COMPUTE_URI = "compute";
    }

    public static class Versions {
        public static final String DEFAULT_API_VERSION = "v2.0";
    }
}