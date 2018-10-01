package io.cloudslang.content.openstack.identity.entities;


public class Domain {
    private final String id;
    private final String name;

    private Domain(Builder builder) {
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

        public Domain build() {
            return new Domain(this);
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
