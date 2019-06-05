package io.cloudslang.content.openstack.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.validator.routines.InetAddressValidator.getInstance;

public class Validators {
    private Validators() {
    }

    public static boolean isJustOneInputValuePresent(String input1, String input2) {
        List<String> inputsList = new ArrayList<>();

        if (isNotBlank(input1)) {
            inputsList.add(input1);
        }

        if (isNotBlank(input2)) {
            inputsList.add(input2);
        }

        return inputsList.size() == 1;
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
        return Optional
                .ofNullable(input)
                .map(b -> getInstance().isValidInet4Address(input) || getInstance().isValidInet6Address(input))
                .orElse(FALSE);
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
        return isNotBlank(input1) && isNotBlank(input2);
    }

    public static boolean isValidBoolean(String input) {
        return Optional
                .ofNullable(input)
                .map(b -> asList(new String[]{"true", "false"}).contains(input))
                .orElse(FALSE);
    }

    public static boolean isPositiveInt(String input) {
        return Optional
                .ofNullable(input)
                .filter(f -> isValidInt(input))
                .map(b -> parseInt(input) >= 0)
                .orElse(FALSE);
    }
}