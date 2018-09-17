package io.cloudslang.content.openstack.compute.wrappers;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.compute.builders.CommonInputs;
import io.cloudslang.content.openstack.compute.builders.api.ApiInputs;

public class InputsWrapper {
    private final HttpClientInputs httpClientInputs;
    private final CommonInputs commonInputs;

    private ApiInputs apiInputs;

    private InputsWrapper(Builder builder) {
        this.httpClientInputs = builder.httpClientInputs;
        this.commonInputs = builder.commonInputs;
    }

    public HttpClientInputs getHttpClientInputs() {
        return httpClientInputs;
    }

    public CommonInputs getCommonInputs() {
        return commonInputs;
    }

    public ApiInputs getApiInputs() {
        return apiInputs;
    }

    public void setApiInputs(ApiInputs apiInputs) {
        this.apiInputs = apiInputs;
    }

    public static class Builder {
        private HttpClientInputs httpClientInputs;
        private CommonInputs commonInputs;

        public InputsWrapper build() {
            return new InputsWrapper(this);
        }

        public Builder withHttpClientInputs(HttpClientInputs httpClientInputs) {
            this.httpClientInputs = httpClientInputs;
            return this;
        }

        public Builder withCommonInputs(CommonInputs commonInputs) {
            this.commonInputs = commonInputs;
            return this;
        }
    }
}
