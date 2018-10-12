package io.cloudslang.content.openstack.entities;

import io.cloudslang.content.httpclient.entities.HttpClientInputs;
import io.cloudslang.content.openstack.builders.CommonInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ApiInputsBuilder;
import io.cloudslang.content.openstack.compute.builders.ServersInputsBuilder;
import io.cloudslang.content.openstack.identity.builders.IdentityInputsBuilder;

public class InputsWrapper {
    private final ApiInputsBuilder apiInputsBuilder;
    private final CommonInputsBuilder commonInputsBuilder;
    private final HttpClientInputs httpClientInputs;
    private final IdentityInputsBuilder identityInputsBuilder;
    private final ServersInputsBuilder serversInputsBuilder;

    private InputsWrapper(Builder builder) {
        this.apiInputsBuilder = builder.apiInputsBuilder;
        this.commonInputsBuilder = builder.commonInputsBuilder;
        this.httpClientInputs = builder.httpClientInputs;
        this.identityInputsBuilder = builder.identityInputsBuilder;
        this.serversInputsBuilder = builder.serversInputsBuilder;
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

    public IdentityInputsBuilder getIdentityInputsBuilder() {
        return identityInputsBuilder;
    }

    public ServersInputsBuilder getServersInputsBuilder() {
        return serversInputsBuilder;
    }

    public static class Builder {
        private ApiInputsBuilder apiInputsBuilder;
        private CommonInputsBuilder commonInputsBuilder;
        private HttpClientInputs httpClientInputs;
        private IdentityInputsBuilder identityInputsBuilder;
        private ServersInputsBuilder serversInputsBuilder;

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

        public Builder withIdentityInputs(IdentityInputsBuilder identityInputsBuilder) {
            this.identityInputsBuilder = identityInputsBuilder;
            return this;
        }

        public Builder withServerInputs(ServersInputsBuilder serversInputsBuilder) {
            this.serversInputsBuilder = serversInputsBuilder;
            return this;
        }
    }
}
