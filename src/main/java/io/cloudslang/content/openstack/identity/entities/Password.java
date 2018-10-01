package io.cloudslang.content.openstack.identity.entities;

public class Password {
    private final User user;

    public User getUser() {
        return user;
    }

    private Password(Builder builder) {
        this.user = builder.user;
    }

    public static class Builder {
        private User user;

        public Password build() {
            return new Password(this);
        }

        public Builder withUser(User inputValue) {
            user = inputValue;
            return this;
        }
    }
}
