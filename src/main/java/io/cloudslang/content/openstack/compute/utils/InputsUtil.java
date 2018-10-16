package io.cloudslang.content.openstack.compute.utils;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import static io.cloudslang.content.openstack.compute.validators.Validators.isValidISO8601DateFormat;
import static io.cloudslang.content.openstack.validators.Validators.isValidInt;
import static io.cloudslang.content.openstack.validators.Validators.isValidUuid;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class InputsUtil {
    private InputsUtil() {
    }

    public static String getValidStringInput(String input, boolean condition, String errorMessage) throws OpenstackException {
        if (isBlank(input)) {
            return EMPTY;
        }

        if (condition) {
            return input;
        }

        throw new OpenstackException(format("Incorrect input value: %s." + errorMessage, input));
    }

    public static String getValidISO8601StringFormat(String input) throws OpenstackException {
        if (isNotBlank(input)) {
            if (isValidISO8601DateFormat(input)) {
                return input;
            }

            throw new OpenstackException(format("Incorrect input value: %s. Specify an ISO 8601 compliant formatted string.", input));
        }

        return null;
    }

    public static int getValidInt(String input, int defaultValue) throws OpenstackException {
        if (isNotBlank(input)) {
            if (isValidInt(input)) {
                return parseInt(input);
            }

            throw new OpenstackException(format("Incorrect input value: %s. Specify a valid integer value.", input));
        }

        return defaultValue;
    }

    public static int getValidIntWithinRange(String input, int minValue, int maxValue, int defaultValue) throws OpenstackException {
        int value = getValidInt(input, defaultValue);

        if (value >= minValue && value <= maxValue) {
            return value;
        }

        throw new OpenstackException(format("Incorrect input value: %s. Specify an integer value between: %s and %s.",
                input, valueOf(minValue), valueOf(maxValue)));
    }

    public static String getValidUuidFormattedString(String input) throws OpenstackException {
        if (isValidUuid(input)) {
            return input;
        }

        throw new OpenstackException(format("Incorrect input value: %s. Specify a UUID formatted value.", input));
    }
}
