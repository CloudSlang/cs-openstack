package io.cloudslang.content.openstack.validators;

import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.validator.routines.InetAddressValidator.getInstance;

public class Validators {
    private Validators() {
    }

    public static boolean isJustOneInputValuePresent(String input1, String input2) {
        return bothValuesArePresent(input1, input2) || bothValuesAreAbsent(input1, input2) ? FALSE : TRUE;
    }

    public static boolean isInputGreaterOrEqualThanThreshold(String input, float threshold) {
        if (isNotBlank(input)) {
            try {
                return parseFloat(input) >= threshold; // micro-version threshold
            } catch (NumberFormatException nfe) {
                return FALSE;
            }
        }

        return FALSE;
    }

    public static boolean isValidHost(String input) {
        return isNotBlank(input)
                && (getInstance().isValidInet4Address(input) || getInstance().isValidInet6Address(input));
    }

    public static boolean isValidInt(String input) {
        try {
            parseInt(input);
            return TRUE;
        } catch (NumberFormatException nfe) {
            return FALSE;
        }
    }

    public static boolean isValidUuid(String input) {
        try {
            //noinspection ResultOfMethodCallIgnored
            UUID.fromString(input);

            return TRUE;
        } catch (IllegalArgumentException exception) {
            return FALSE;
        }
    }

    public static boolean bothValuesArePresent(String input1, String input2) {
        return isNotBlank(input1) && isNotBlank(input2) ? TRUE : FALSE;
    }

    private static boolean bothValuesAreAbsent(String input1, String input2) {
        return isBlank(input1) && isBlank(input2) ? TRUE : FALSE;
    }
}