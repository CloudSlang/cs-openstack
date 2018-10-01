package io.cloudslang.content.openstack.identity.entities;

import java.util.List;

public class Identity {
    private final List<String> methods;
    private final Password password;

    private Identity(Builder builder) {
        this.methods = builder.methods;
        this.password = builder.password;
    }

    public List<String> getMethods() {
        return methods;
    }

    public Password getPassword() {
        return password;
    }

    public static class Builder {
        private List<String> methods;
        private Password password;

        public Identity build() {
            return new Identity(this);
        }

        public Builder withMethods(List<String> inputValue) {
            methods = inputValue;
            return this;
        }

        public Builder withPassword(Password inputValue) {
            password = inputValue;
            return this;
        }
    }
}
