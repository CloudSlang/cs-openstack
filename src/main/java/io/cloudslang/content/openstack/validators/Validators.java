package io.cloudslang.content.openstack.validators;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.validator.routines.InetAddressValidator.getInstance;

public class Validators {
    private Validators() {
    }

    public static boolean isJustOneInputValuePresent(String input1, String input2) {
        List<String> inputsList = new ArrayList<>();

        ofNullable(input1)
                .filter(StringUtils::isNotEmpty)
                .ifPresent(add -> inputsList.add(input1));
        ofNullable(input2)
                .filter(StringUtils::isNotEmpty)
                .ifPresent(add -> inputsList.add(input2));

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
        return ofNullable(input)
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

    public static boolean isValidBoolean(String input) {
        return ofNullable(input)
                .map(b -> of("true", "false").anyMatch(f -> f.equalsIgnoreCase(input)))
                .orElse(FALSE);
    }

    public static boolean isPositiveInt(String input) {
        return ofNullable(input)
                .filter(f -> isValidInt(input))
                .map(b -> parseInt(input) >= 0)
                .orElse(FALSE);
    }
}