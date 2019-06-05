package io.cloudslang.content.openstack.entities;

public class Constants {
    private Constants() {
    }

    public class Headers {
        public static final String ALLOW_ALL = "allow_all";
        public static final String BROWSER_COMPATIBLE = "browser_compatible";
        public static final String STRICT = "strict";
        public static final String REQUEST_ID_HEADER_PREFIX = "req-";
        public static final String TOKEN = "token";
        public static final String X_AUTH_TOKEN = "X-Auth-Token";
        public static final String X_OPENSTACK_REQUEST_ID = "X-Openstack-Request-Id";
    }

    public class Miscellaneous {
        public static final String AMPERSAND = "&";
        public static final String BLANK_SPACE = " ";
        public static final String COLON = ":";
        public static final String EQUAL = "=";
        public static final String QUESTION_MARK = "?";
        public static final String SLASH = "/";
    }

    public class Requests {
        public static final int THREADS_NUMBER = 10;
    }

    public class Responses {
        public static final String REQUEST_TRACKING_ID = "requestTrackingId";
    }

    public class Values {
        public static final String DEFAULT_TIMEOUT_VALUE = "0";
        public static final float THRESHOLD_VERSION_FOR_REQUEST_UUID_PRESENCE = 2.46f;
    }
}
