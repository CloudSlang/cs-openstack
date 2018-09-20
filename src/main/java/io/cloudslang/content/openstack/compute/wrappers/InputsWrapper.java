package io.cloudslang.content.openstack.compute.wrappers;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.compute.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputsBuilder;

public class InputsWrapper {
    private final HttpClientInputs httpClientInputs;
    private final CommonInputsBuilder commonInputsBuilder;

    private ApiInputsBuilder apiInputsBuilder;

    private InputsWrapper(Builder builder) {
        this.httpClientInputs = builder.httpClientInputs;
        this.commonInputsBuilder = builder.commonInputsBuilder;
    }

    public HttpClientInputs getHttpClientInputs() {
        return httpClientInputs;
    }

    public CommonInputsBuilder getCommonInputsBuilder() {
        return commonInputsBuilder;
    }

    public ApiInputsBuilder getApiInputsBuilder() {
        return apiInputsBuilder;
    }

    public void setApiInputsBuilder(ApiInputsBuilder apiInputsBuilder) {
        this.apiInputsBuilder = apiInputsBuilder;
    }

    public static class Builder {
        private HttpClientInputs httpClientInputs;
        private CommonInputsBuilder commonInputsBuilder;

        public InputsWrapper build() {
            return new InputsWrapper(this);
        }

        public Builder withHttpClientInputs(HttpClientInputs httpClientInputs) {
            this.httpClientInputs = httpClientInputs;
            return this;
        }

        public Builder withCommonInputs(CommonInputsBuilder commonInputsBuilder) {
            this.commonInputsBuilder = commonInputsBuilder;
            return this;
        }
    }
}
