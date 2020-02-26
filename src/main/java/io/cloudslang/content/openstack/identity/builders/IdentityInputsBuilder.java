package io.cloudslang.content.openstack.identity.builders;

import io.cloudslang.content.openstack.identity.entities.Auth;

import static java.lang.Boolean.parseBoolean;

public class IdentityInputsBuilder {
    private final Auth auth;
    private final Boolean noCatalog;

    public Auth getAuth() {
        return auth;
    }

    public Boolean getNoCatalog() {
        return noCatalog;
    }

    private IdentityInputsBuilder(Builder builder) {
        this.auth = builder.auth;
        this.noCatalog = builder.noCatalog;
    }

    public static class Builder {
        private Auth auth;
        private Boolean noCatalog;

        public IdentityInputsBuilder build() {
            return new IdentityInputsBuilder(this);
        }

        public Builder withAuth(Auth inputValue) {
            auth = inputValue;
            return this;
        }

        public Builder withNoCatalog(String inputValue) {
            noCatalog = !parseBoolean(inputValue);
            return this;
        }
    }
}
