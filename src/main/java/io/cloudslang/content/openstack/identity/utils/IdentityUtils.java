package io.cloudslang.content.openstack.identity.utils;

import io.cloudslang.content.openstack.exceptions.OpenstackException;
import io.cloudslang.content.openstack.identity.entities.Domain;
import io.cloudslang.content.openstack.identity.entities.User;

import static io.cloudslang.content.openstack.validators.Validators.validateAtLeastOneValueIsPresent;
import static io.cloudslang.content.openstack.validators.Validators.validateOnlyOneInputValueIsPresent;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class IdentityUtils {
    private IdentityUtils() {
    }

    public static Domain buildDomain(String domainId, String domainUserName) throws OpenstackException {
        validateOnlyOneInputValueIsPresent(domainId, domainUserName);
        validateAtLeastOneValueIsPresent(domainId, domainUserName);

        if (isNotBlank(domainId) && isBlank(domainUserName)) {
            //noinspection unchecked
            return new Domain.Builder()
                    .withId(domainId)
                    .build();
        }

        if (isBlank(domainId) && isNotBlank(domainUserName)) {
            //noinspection unchecked
            return new Domain.Builder()
                    .withName(domainUserName)
                    .build();
        }

        return null;
    }

    public static User buildUser(Domain domain, String userId, String username, String password) throws OpenstackException {
        validateOnlyOneInputValueIsPresent(userId, username);
        validateAtLeastOneValueIsPresent(userId, username);

        if (isNotBlank(userId) && isBlank(username)) {
            //noinspection unchecked
            return new User.Builder()
                    .withDomain(domain)
                    .withId(userId)
                    .withPassword(password)
                    .build();
        }

        if (isBlank(userId) && isNotBlank(username)) {
            //noinspection unchecked
            return new User.Builder()
                    .withDomain(domain)
                    .withName(username)
                    .withPassword(password)
                    .build();
        }


        return null;
    }
}
