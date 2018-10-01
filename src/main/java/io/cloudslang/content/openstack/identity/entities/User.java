package io.cloudslang.content.openstack.identity.entities;

import com.google.gson.annotations.SerializedName;

public class User {
    private final Domain domain;
    private final String id;
    private final String name;
    private final String password;

    @SerializedName("password_expires_at")
    private final String passwordExpiresAt;

    private User(Builder builder) {
        this.domain = builder.domain;
        this.id = builder.id;
        this.name = builder.name;
        this.password = builder.password;
        this.passwordExpiresAt = builder.passwordExpiresAt;
    }

    public Domain getDomain() {
        return domain;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordExpiresAt() {
        return passwordExpiresAt;
    }

    public static class Builder {
        private Domain domain;
        private String id;
        private String name;
        private String password;
        private String passwordExpiresAt;

        public User build() {
            return new User(this);
        }

        public Builder withDomain(Domain inputValue) {
            domain = inputValue;
            return this;
        }

        public Builder withId(String inputValue) {
            id = inputValue;
            return this;
        }

        public Builder withName(String inputValue) {
            name = inputValue;
            return this;
        }

        public Builder withPassword(String inputValue) {
            password = inputValue;
            return this;
        }

        public Builder withPasswordExpiresAt(String inputValue) {
            passwordExpiresAt = inputValue;
            return this;
        }
    }
}
