package io.cloudslang.content.openstack.compute.entities;

public class Constants {
    private Constants() {
    }

    public static class Actions {
        public static class Api {
            public static final String GET_API_VERSION_DETAILS = "GetApiVersionDetails";
            public static final String LIST_ALL_MAJOR_VERSIONS = "ListAllMajorVersions";
        }

        public static class Identity {
            public static final String PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION = "PasswordAuthenticationWithUnscopedAuthorization";
        }
    }

    public static class Api {
        public static final String API = "api";
        public static final String SERVERS = "servers";
    }

    public static class Headers {
        public static final String X_SUBJECT_TOKEN = "X-Subject-Token";
    }

    public static class Miscellaneous {
        public static final String BLANK_SPACE = " ";
        public static final String SLASH = "/";
    }

    public static class Responses {
        public static final String EXPIRES_AT = "expiresAt";
        public static final String NEVER = "never";
        public static final String TOKEN = "token";
    }

    public static class Uri {
        public static final String COMPUTE_URI = "compute";
    }

    public static class Values {
        public static final String DEFAULT_TIMEOUT_VALUE = "0";
    }

    public static class Versions {
        public static final String DEFAULT_API_VERSION = "v2.0";
        public static final String DEFAULT_IDENTITY_VERSION = "v3";
    }
}
