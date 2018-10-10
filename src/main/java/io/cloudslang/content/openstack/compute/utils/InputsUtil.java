package io.cloudslang.content.openstack.compute.utils;

import io.cloudslang.content.openstack.exceptions.OpenstackException;

import java.util.Optional;
import java.util.stream.Stream;

import static io.cloudslang.content.openstack.entities.Constants.Values.ISO8601_PATTERN;
import static io.cloudslang.content.openstack.validators.Validators.isValidInt;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class InputsUtil {
    private InputsUtil() {
    }

    public static String getValidStringInput(String input, boolean condition, String errorMessage) throws OpenstackException {
        if (condition) {
            return input;
        }

        throw new OpenstackException(format("Incorrect input value: %s." + errorMessage, input));
    }

    public static String getValidISO8601StringFormat(String input) throws OpenstackException {
        return Optional
                .of(parse(input, ofPattern(ISO8601_PATTERN)).toString())
                .orElseThrow(() -> new OpenstackException(format("Incorrect input value: %s. Specify an ISO 8601 compliant formatted string.",
                        input)));
    }

    public static int getValidInt(String input) throws OpenstackException {
        if (isValidInt(input)) {
            return parseInt(input);
        }

        throw new OpenstackException(format("Incorrect input value: %s. Specify a valid integer value.", input));
    }

    public static boolean shouldBeTrue(String input) {
        return Stream
                .of("1", "t", "true", "on", "y", "yes")
                .anyMatch(input::equalsIgnoreCase);
    }
}
