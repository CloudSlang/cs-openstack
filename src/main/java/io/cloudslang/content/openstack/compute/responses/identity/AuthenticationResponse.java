package io.cloudslang.content.openstack.compute.responses.identity;

import com.google.gson.annotations.SerializedName;
import io.cloudslang.content.openstack.compute.entities.identity.Domain;
import io.cloudslang.content.openstack.compute.entities.identity.Token;
import io.cloudslang.content.openstack.compute.entities.identity.User;

import java.util.List;
import java.util.Objects;

public class AuthenticationResponse {
    private Domain domain;
    private Token token;
    private User user;

    // A list of one or two audit IDs. An audit ID is a unique, randomly generated, URL-safe string that you can use to
    // track a token. The first audit ID is the current audit ID for the token. The second audit ID is present for only
    // re-scoped tokens and is the audit ID from the token before it was re-scoped. A re- scoped token is one that was
    // exchanged for another token of the same or different scope. You can use these audit IDs to track the use of a token
    // or chain of tokens across multiple requests and endpoints without exposing the token ID to non-privileged users.
    @SerializedName("audit_ids")
    private List<String> auditIds;

    private List<String> methods;

    // The date and time when the token expires. The date and time stamp format is ISO 8601: CCYY-MM-DDThh:mm:ss.sssZ
    // Example: 2015-08-27T09:49:58.000000Z. A null value indicates that the token never expires.
    @SerializedName("expires_at")
    private String expiresAt;

    // The date and time when the token was issued. The date and time stamp format is ISO 8601: CCYY-MM-DDThh:mm:ss.sssZ
    // Example: 2015-08-27T09:49:58.000000Z.
    @SerializedName("issued_at")
    private String issuedAt;

    // The ID of the user. Required if you do not specify the user name.
    private String id;

    // The user name. Required if you do not specify the ID of the user. If you specify the user name, you must also
    // specify the domain, by ID or name.
    private String name;

    public AuthenticationResponse(Domain domain, Token token, User user, List<String> auditIds, List<String> methods,
                                  String expiresAt, String issuedAt, String id, String name) {
        this.domain = domain;
        this.token = token;
        this.user = user;
        this.auditIds = auditIds;
        this.methods = methods;
        this.expiresAt = expiresAt;
        this.issuedAt = issuedAt;
        this.id = id;
        this.name = name;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getAuditIds() {
        return auditIds;
    }

    public void setAuditIds(List<String> auditIds) {
        this.auditIds = auditIds;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationResponse that = (AuthenticationResponse) o;
        return Objects.equals(domain, that.domain) &&
                Objects.equals(token, that.token) &&
                Objects.equals(user, that.user) &&
                Objects.equals(auditIds, that.auditIds) &&
                Objects.equals(methods, that.methods) &&
                Objects.equals(expiresAt, that.expiresAt) &&
                Objects.equals(issuedAt, that.issuedAt) &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, token, user, auditIds, methods, expiresAt, issuedAt, id, name);
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "expiresAt='" + expiresAt + '\'' +
                ", issuedAt='" + issuedAt + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
