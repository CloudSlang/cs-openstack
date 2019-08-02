package io.cloudslang.content.openstack.compute.utils;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Optional;

import static io.cloudslang.content.openstack.compute.validators.Validators.isValidISO8601DateFormat;
import static io.cloudslang.content.openstack.validators.Validators.isValidInt;
import static io.cloudslang.content.openstack.validators.Validators.isValidUuid;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class InputsUtil {
    private InputsUtil() {
    }

    public static String getValidStringInput(String input, boolean condition, String errorMessage) throws OpenstackException {
        return isBlank(input) ? EMPTY :
                Optional
                        .of(input)
                        .filter(f -> condition)
                        .orElseThrow(() -> new OpenstackException(format("Incorrect input value: %s. Error occurred: %s", input, errorMessage)));
    }

    public static String getValidISO8601StringFormat(String input) throws OpenstackException {
        return isBlank(input) ? null :
                Optional
                        .of(input)
                        .filter(f -> isValidISO8601DateFormat(input))
                        .orElseThrow(() -> new OpenstackException(format("Incorrect input value: %s. Specify an ISO 8601 compliant formatted string.", input)));
    }

    public static int getValidInt(String input, int defaultValue) throws OpenstackException {
        return isBlank(input) ? defaultValue :
                Optional
                        .of(input)
                        .filter(f -> isValidInt(input))
                        .map(i -> parseInt(input))
                        .orElseThrow(() -> new OpenstackException(format("Incorrect input value: %s. Specify a valid integer value.", input)));
    }

    public static int getValidIntWithinRange(String input, int minValue, int maxValue, int defaultValue) throws OpenstackException {
        int value = getValidInt(input, defaultValue);

        return Optional
                .of(value)
                .filter(f -> value >= minValue && value <= maxValue)
                .orElseThrow(() -> new OpenstackException(format("Incorrect input value: %s. Specify an integer value between: %s and %s.",
                        input, valueOf(minValue), valueOf(maxValue))));
    }

    public static String getValidUuidFormattedString(String input) throws OpenstackException {
        return Optional
                .of(input)
                .filter(f -> isValidUuid(input))
                .orElseThrow(() -> new OpenstackException(format("Incorrect input value: %s. Specify a UUID formatted value.", input)));
    }
}
