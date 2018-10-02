package io.cloudslang.content.openstack.validators;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Validators {
    private Validators() {
    }

    public static void validateOnlyOneInputValueIsPresent(String input1, String input2) throws OpenstackException {
        if (isNotBlank(input1) && isNotBlank(input2)) {
            throw new OpenstackException(format("Both input: '%s', '%s' values are specified. Specify at least one of it but not both.",
                    input1, input2));
        }
    }

    public static void validateAtLeastOneValueIsPresent(String input1, String input2) throws OpenstackException {
        if (isBlank(input1) && isBlank(input2)) {
            throw new OpenstackException(format("Both input: '%s', '%s' values are empty. Specify at least one of it but not both.",
                    input1, input2));
        }
    }
}