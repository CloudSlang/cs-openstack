package io.cloudslang.content.openstack.identity.entities;

public class Constants {
    private Constants() {
    }

    public static class Actions {
        public static final String GET_AVAILABLE_PROJECT_SCOPES = "GetAvailableProjectScopes";
        public static final String GET_SERVICE_CATALOG = "GetServiceCatalog";
        public static final String PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION = "PasswordAuthenticationWithUnscopedAuthorization";
    }

    public static class Api {
        public static final String IDENTITY = "identity";
    }

    public static class Headers {
        public static final String X_SUBJECT_TOKEN = "X-Subject-Token";

    }

    public static class QueryParams {
        public static final String NO_CATALOG = "nocatalog";

    }

    public static class Responses {
        public static final String EXPIRES_AT = "expiresAt";
        public static final String NEVER = "never";
    }

    public static class Values {
        public static final String IDENTITY_PORT = "5000";
    }

    public static class Versions {
        public static final String DEFAULT_IDENTITY_VERSION = "v3";
    }
}
