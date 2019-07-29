package io.cloudslang.content.openstack.identity.utils;

import io.cloudslang.content.openstack.identity.entities.Domain;
import io.cloudslang.content.openstack.identity.entities.User;

import java.util.Optional;

import static io.cloudslang.content.openstack.validators.Validators.isJustOneInputValuePresent;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class IdentityUtils {
    private IdentityUtils() {
    }

    public static Domain buildDomain(String domainId, String domainUserName) {
        return Optional
                .of(isJustOneInputValuePresent(domainId, domainUserName))
                .map(domain -> getDomain(domainId, domainUserName))
                .orElse(null);

    }

    private static Domain getDomain(String domainId, String domainUserName) {
        return Optional
                .ofNullable(domainId)
                .filter(f -> isNotEmpty(domainId) && isBlank(domainUserName))
                .map(domain -> new Domain.Builder()
                        .withId(domainId)
                        .build())
                .orElse(Optional
                        .ofNullable(domainUserName)
                        .filter(f -> isNotEmpty(domainUserName))
                        .map(anotherDomain -> new Domain.Builder()
                                .withName(domainUserName)
                                .build())
                        .orElse(null));
    }

    public static User buildUser(Domain domain, String userId, String username, String password) {
        return Optional
                .of(isJustOneInputValuePresent(userId, username))
                .map(user -> getUser(domain, userId, username, password))
                .orElse(null);
    }

    private static User getUser(Domain domain, String userId, String username, String password) {
        //noinspection DuplicateExpressions
        return Optional
                .ofNullable(userId)
                .filter(outerFilter -> isNotEmpty(userId) && isBlank(username))
                .map(user -> new User.Builder()
                        .withDomain(domain)
                        .withId(userId)
                        .withPassword(password)
                        .build())
                .orElse(Optional
                        .ofNullable(username)
                        .filter(innerFilter -> isNotEmpty(username))
                        .map(anotherUser -> new User.Builder()
                                .withDomain(domain)
                                .withId(userId)
                                .withPassword(password)
                                .build())
                        .orElse(null));
    }
}
