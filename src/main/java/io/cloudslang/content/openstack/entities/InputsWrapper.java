package io.cloudslang.content.openstack.entities;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputsBuilder;

public class InputsWrapper {
    private final ApiInputsBuilder apiInputsBuilder;
    private final CommonInputsBuilder commonInputsBuilder;
    private final HttpClientInputs httpClientInputs;

    private InputsWrapper(Builder builder) {
        this.httpClientInputs = builder.httpClientInputs;
        this.commonInputsBuilder = builder.commonInputsBuilder;
        this.apiInputsBuilder = builder.apiInputsBuilder;
    }

    public ApiInputsBuilder getApiInputsBuilder() {
        return apiInputsBuilder;
    }

    public CommonInputsBuilder getCommonInputsBuilder() {
        return commonInputsBuilder;
    }

    public HttpClientInputs getHttpClientInputs() {
        return httpClientInputs;
    }

    public static class Builder {
        private ApiInputsBuilder apiInputsBuilder;
        private CommonInputsBuilder commonInputsBuilder;
        private HttpClientInputs httpClientInputs;

        public InputsWrapper build() {
            return new InputsWrapper(this);
        }

        public Builder withApiInputs(ApiInputsBuilder apiInputsBuilder) {
            this.apiInputsBuilder = apiInputsBuilder;
            return this;
        }

        public Builder withCommonInputs(CommonInputsBuilder commonInputsBuilder) {
            this.commonInputsBuilder = commonInputsBuilder;
            return this;
        }

        public Builder withHttpClientInputs(HttpClientInputs httpClientInputs) {
            this.httpClientInputs = httpClientInputs;
            return this;
        }
    }
}
