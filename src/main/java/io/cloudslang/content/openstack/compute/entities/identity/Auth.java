package io.cloudslang.content.openstack.compute.entities.identity;

public class Auth {
    private final Identity identity;
    private final Scope scope;

    private Auth(Builder builder) {
        this.identity = builder.identity;
        this.scope = builder.scope;
    }

    public Identity getIdentity() {
        return identity;
    }

    public Scope getScope() {
        return scope;
    }

    public static class Builder {
        private Identity identity;
        private Scope scope;

        public Auth build() {
            return new Auth(this);
        }

        public Builder withIdentity(Identity inputValue) {
            identity = inputValue;
            return this;
        }

        public Builder withScope(Scope inputValue) {
            scope = inputValue;
            return this;
        }
    }
}
