package io.cloudslang.content.openstack.compute.entities.identity;

public class Scope {
    private final Domain domain;
    private final System system;

    private Scope(Builder builder) {
        this.domain = builder.domain;
        this.system = builder.system;
    }

    public Domain getDomain() {
        return domain;
    }

    public System getSystem() {
        return system;
    }

    public static class Builder {
        private Domain domain;
        private System system;

        public Scope build() {
            return new Scope(this);
        }

        public Builder withDomain(Domain inputValue) {
            domain = inputValue;
            return this;
        }

        public Builder withName(System inputValue) {
            system = inputValue;
            return this;
        }
    }
}
