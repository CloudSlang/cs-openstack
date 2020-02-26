package io.cloudslang.content.openstack.identity.utils;

import io.cloudslang.content.openstack.identity.entities.Domain;
import io.cloudslang.content.openstack.identity.entities.User;
import org.apache.commons.lang3.StringUtils;

import static io.cloudslang.content.openstack.validators.Validators.isJustOneInputValuePresent;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class IdentityUtils {
    private IdentityUtils() {
    }

    public static Domain buildDomain(String domainId, String domainUserName) {
        return of(isJustOneInputValuePresent(domainId, domainUserName))
                .map(domain -> getDomain(domainId, domainUserName))
                .orElse(null);

    }

    public static User buildUser(Domain domain, String userId, String username, String password) {
        return of(isJustOneInputValuePresent(userId, username))
                .map(user -> getUser(domain, userId, username, password))
                .orElse(null);
    }

    private static Domain getDomain(String domainId, String domainUserName) {
        return ofNullable(domainId)
                .filter(f -> isNotEmpty(domainId) && isBlank(domainUserName))
                .map(withDomain -> new Domain.Builder()
                        .withId(domainId)
                        .build())
                .orElse(ofNullable(domainUserName)
                        .filter(StringUtils::isNotEmpty)
                        .map(withDomainUserName -> new Domain.Builder()
                                .withName(domainUserName)
                                .build())
                        .orElse(null));
    }

    private static User getUser(Domain domain, String userId, String username, String password) {
        return ofNullable(userId)
                .filter(f -> isNotEmpty(userId) && isBlank(username))
                .map(user -> new User.Builder()
                        .withDomain(domain)
                        .withId(userId)
                        .withPassword(password)
                        .build())
                .orElse(ofNullable(username)
                        .filter(StringUtils::isNotEmpty)
                        .map(anotherUser -> new User.Builder()
                                .withDomain(domain)
                                .withId(userId)
                                .withPassword(password)
                                .build())
                        .orElse(null));
    }
}
