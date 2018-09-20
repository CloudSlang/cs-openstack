package io.cloudslang.content.openstack.compute.entities.identity;

import java.util.List;

public class Token {
    private final User user;

    private final List<String> auditIds;
    private final List<String> methods;

    private final String expiresAt;
    private final String issuedAt;

    private Token(Builder builder) {
        this.user = builder.user;
        this.auditIds = builder.auditIds;
        this.methods = builder.methods;
        this.expiresAt = builder.expiresAt;
        this.issuedAt = builder.issuedAt;
    }

    public User getUser() {
        return user;
    }

    public List<String> getAuditIds() {
        return auditIds;
    }

    public List<String> getMethods() {
        return methods;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public static class Builder {
        private User user;
        private List<String> auditIds;
        private List<String> methods;
        private String expiresAt;
        private String issuedAt;

        public Token build() {
            return new Token(this);
        }

        public Builder withUser(User inputValue) {
            user = inputValue;
            return this;
        }

        public Builder withAuditIds(List<String> inputValue) {
            auditIds = inputValue;
            return this;
        }

        public Builder withMethods(List<String> inputValue) {
            methods = inputValue;
            return this;
        }

        public Builder withExpiresAt(String inputValue) {
            expiresAt = inputValue;
            return this;
        }

        public Builder withIssuedAt(String inputValue) {
            issuedAt = inputValue;
            return this;
        }
    }
}
