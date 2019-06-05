package io.cloudslang.content.openstack.compute.validators;

import org.apache.commons.validator.routines.InetAddressValidator;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.ZonedDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class Validators {
    private Validators() {
    }

    public static boolean shouldBeTrue(String input) {
        return Stream
                .of("1", "t", "true", "on", "y", "yes")
                .anyMatch(input::equalsIgnoreCase);
    }

    public static boolean isValidISO8601DateFormat(String input) {
        try {
            parse(input, ISO_OFFSET_DATE_TIME);

            return TRUE;
        } catch (DateTimeParseException dtpe) {
            return FALSE;
        }
    }

    public static boolean isIpV4(String input) {
        return new InetAddressValidator().isValidInet4Address(input);
    }

    public static boolean isIpV6(String input) {
        return new InetAddressValidator().isValidInet6Address(input);
    }
}
