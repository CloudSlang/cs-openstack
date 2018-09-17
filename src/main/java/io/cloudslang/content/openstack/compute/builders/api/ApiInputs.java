package io.cloudslang.content.openstack.compute.builders.api;

public class ApiInputs {
    private final String apiVersion;

    private ApiInputs(Builder builder) {
        this.apiVersion = builder.apiVersion;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public static class Builder {
        private String apiVersion;

        public ApiInputs build() {
            return new ApiInputs(this);
        }

        public Builder withApiVersion(String inputValue) {
            apiVersion = inputValue;
            return this;
        }
    }
}
