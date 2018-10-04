package io.cloudslang.content.openstack.compute.builders;

import static io.cloudslang.content.openstack.compute.entities.Constants.Versions.DEFAULT_COMPUTE_VERSION;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class ApiInputsBuilder {
    private final String apiVersion;

    private ApiInputsBuilder(Builder builder) {
        this.apiVersion = builder.apiVersion;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public static class Builder {
        private String apiVersion;

        public ApiInputsBuilder build() {
            return new ApiInputsBuilder(this);
        }

        public Builder withApiVersion(String inputValue) {
            apiVersion = defaultIfEmpty(inputValue, DEFAULT_COMPUTE_VERSION);
            return this;
        }
    }
}
