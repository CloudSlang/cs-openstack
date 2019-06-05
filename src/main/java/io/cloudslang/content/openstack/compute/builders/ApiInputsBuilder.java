package io.cloudslang.content.openstack.compute.builders;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static io.cloudslang.content.openstack.compute.entities.Constants.Versions.DEFAULT_COMPUTE_VERSION;

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
            apiVersion = Optional.ofNullable(inputValue).filter(StringUtils::isNotEmpty).orElse(DEFAULT_COMPUTE_VERSION);
            return this;
        }
    }
}
