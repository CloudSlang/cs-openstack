package io.cloudslang.content.openstack.compute.builders;

import static io.cloudslang.content.openstack.compute.entities.Constants.Miscellaneous.SLASH;
import static io.cloudslang.content.openstack.compute.entities.Constants.Versions.DEFAULT_API_VERSION;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.appendIfMissing;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.join;

public class CommonInputsBuilder {
    private final String endpoint;
    private final String action;
    private final String api;
    private final String version;

    private CommonInputsBuilder(Builder builder) {
        this.endpoint = builder.endpoint;
        this.action = builder.action;
        this.api = builder.api;
        this.version = builder.version;
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

    public static class Builder {
        private String endpoint;
        private String action;
        private String api;
        private String version;

        public CommonInputsBuilder build() {
            return new CommonInputsBuilder(this);
        }

        public Builder withEndpoint(String inputValue) {
            endpoint = appendIfMissing(inputValue, SLASH);
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
            version = appendIfMissing(defaultIfEmpty(inputValue, DEFAULT_API_VERSION), SLASH);
            return this;
        }
    }
}
