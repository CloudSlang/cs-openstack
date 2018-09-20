package io.cloudslang.content.openstack.compute.builders.api;

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
            apiVersion = inputValue;
            return this;
        }
    }
}
