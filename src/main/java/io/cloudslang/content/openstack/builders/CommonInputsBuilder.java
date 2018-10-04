package io.cloudslang.content.openstack.builders;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class CommonInputsBuilder {
    private final String endpoint;
    private final String action;
    private final String api;
    private final String version;
    private final String token;

    private CommonInputsBuilder(Builder builder) {
        this.endpoint = builder.endpoint;
        this.action = builder.action;
        this.api = builder.api;
        this.version = builder.version;
        this.token = builder.token;
    }

    public String getAction() {
        return action;
    }

    public String getApi() {
        return api;
    }

    public String getVersion() {
        return version;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getToken() {
        return token;
    }

    public static class Builder {
        private String endpoint;
        private String action;
        private String api;
        private String version;
        private String token;

        public CommonInputsBuilder build() {
            return new CommonInputsBuilder(this);
        }

        public Builder withEndpoint(String inputValue) {
            endpoint = inputValue;
            return this;
        }

        public Builder withAction(String inputValue) {
            action = inputValue;
            return this;
        }

        public Builder withApi(String inputValue) {
            api = defaultIfEmpty(inputValue, EMPTY);
            return this;
        }

        public Builder withVersion(String inputValue) {
            version = inputValue;
            return this;
        }

        public Builder withToken(String inputValue) {
            token = inputValue;
            return this;
        }
    }
}
