package io.cloudslang.content.openstack.entities;

public class Constants {
    private Constants() {
    }

    public static class Headers {
        public static final String ALLOW_ALL = "allow_all";
        public static final String BROWSER_COMPATIBLE = "browser_compatible";
        public static final String STRICT = "strict";
        public static final String REQUEST_ID_HEADER_PREFIX = "req-";
        public static final String TOKEN = "token";
        public static final String X_AUTH_TOKEN = "X-Auth-Token";
        public static final String X_OPENSTACK_REQUEST_ID = "X-Openstack-Request-Id";
    }

    public static class Miscellaneous {
        public static final String BLANK_SPACE = " ";
        public static final String COLON = ":";
        public static final String QUESTION_MARK = "?";
        public static final String SLASH = "/";
    }

    public static class Patterns {
        public static final String HOST_PATTERN = "([^\\[/?#:]*|\\[[\\p{XDigit}\\:\\.]*[%\\p{Alnum}]*\\])";
    }

    public static class Responses {
        public static final String REQUEST_TRACKING_ID = "requestTrackingId";
    }

    public static class Values {
        public static final String DEFAULT_TIMEOUT_VALUE = "0";
        public static final String ISO8601_PATTERN = "CCYY-MM-DDThh:mm:ss±hh:mm";
        public static final float VERSION_THRESHOLD_FLOAT = 2.46f;
    }
}
