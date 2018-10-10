package io.cloudslang.content.openstack.validators;

import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.regex.PatternSyntaxException;

import static io.cloudslang.content.openstack.entities.Constants.Patterns.HOST_PATTERN;
import static io.cloudslang.content.openstack.entities.Constants.Values.VERSION_THRESHOLD_FLOAT;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Validators {
    private Validators() {
    }

    public static boolean isJustOneInputValuePresent(String input1, String input2) {
        return bothValuesArePresent(input1, input2) || bothValuesAreAbsent(input1, input2) ? FALSE : TRUE;
    }

    public static boolean isVersionGreaterOrEqualThanThreshold(String input) {
        if (isNotBlank(input)) {
            try {
                return parseFloat(input) >= VERSION_THRESHOLD_FLOAT; // micro-version threshold for tracking requests
            } catch (NumberFormatException nfe) {
                return FALSE;
            }
        }

        return false;
    }

    public static boolean isValidHost(String input) {
        try {
            return matches(HOST_PATTERN, input);
        } catch (PatternSyntaxException pse) {
            return FALSE;
        }
    }

    public static boolean isValidInt(String input) {
        try {
            parseInt(input);
            return TRUE;
        } catch (NumberFormatException nfe) {
            return FALSE;
        }
    }

    public static boolean isIpV4(String input) {
        return new InetAddressValidator().isValidInet4Address(input);
    }

    public static boolean isIpV6(String input) {
        return new InetAddressValidator().isValidInet6Address(input);
    }

    private static boolean bothValuesArePresent(String input1, String input2) {
        return isNotBlank(input1) && isNotBlank(input2) ? TRUE : FALSE;
    }

    private static boolean bothValuesAreAbsent(String input1, String input2) {
        return isBlank(input1) && isBlank(input2) ? TRUE : FALSE;
    }
}