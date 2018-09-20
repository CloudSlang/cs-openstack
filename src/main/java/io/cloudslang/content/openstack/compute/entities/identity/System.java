package io.cloudslang.content.openstack.compute.entities.identity;

public class System {
    private final Boolean all;

    private System(Builder builder) {
        this.all = builder.all;
    }

    public Boolean getAll() {
        return all;
    }

    public static class Builder {
        private Boolean all;

        public System build() {
            return new System(this);
        }

        public Builder withAll(Boolean inputValue) {
            all = inputValue;
            return this;
        }
    }
}
