package io.cloudslang.content.openstack.identity.entities;

public class Constants {
    private Constants() {
    }

    public static class Actions {
        public static final String PASSWORD_AUTHENTICATION_WITH_UNSCOPED_AUTHORIZATION = "PasswordAuthenticationWithUnscopedAuthorization";
    }

    public static class Headers {
        public static final String X_SUBJECT_TOKEN = "X-Subject-Token";
    }

    public static class Responses {
        public static final String EXPIRES_AT = "expiresAt";
        public static final String NEVER = "never";
        public static final String TOKEN = "token";
    }

    public static class Versions {
        public static final String DEFAULT_IDENTITY_VERSION = "v3";
    }
}
