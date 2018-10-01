package io.cloudslang.content.openstack.identity.entities;

public class Roles {
    private final String id;
    private final String name;

    private Roles(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String id;
        private String name;

        public Roles build() {
            return new Roles(this);
        }

        public Builder withId(String inputValue) {
            id = inputValue;
            return this;
        }

        public Builder withName(String inputValue) {
            name = inputValue;
            return this;
        }
    }
}
