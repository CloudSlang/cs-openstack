package io.cloudslang.content.openstack.identity.requests;

import io.cloudslang.content.openstack.identity.entities.Auth;

import java.util.Objects;

public class TokenRequest {
    private Auth auth;

    public TokenRequest(Auth auth) {
        this.auth = auth;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenRequest that = (TokenRequest) o;
        return Objects.equals(auth, that.auth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auth);
    }

    @Override
    public String toString() {
        return "TokenRequest{" +
                "auth=" + auth +
                '}';
    }
}
