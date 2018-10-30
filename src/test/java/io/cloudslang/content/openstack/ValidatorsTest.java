package io.cloudslang.content.openstack;

import org.junit.Test;

import static io.cloudslang.content.openstack.validators.Validators.isInputGreaterOrEqualThanThreshold;
import static io.cloudslang.content.openstack.validators.Validators.isJustOneInputValuePresent;
import static io.cloudslang.content.openstack.validators.Validators.isValidHost;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorsTest {
    @Test
    public void shouldBeFalseWhenBothNull() {
        assertFalse(isJustOneInputValuePresent(null, null));
    }

    @Test
    public void shouldBeFalseWhenBothEmpty() {
        assertFalse(isJustOneInputValuePresent("", ""));
    }

    @Test
    public void shouldBeFalseWhenBothBlanks() {
        assertFalse(isJustOneInputValuePresent(" ", "  "));
    }

    @Test
    public void shouldBeFalseWhenBothPresent() {
        assertFalse(isJustOneInputValuePresent("value1", "value2"));
    }

    @Test
    public void shouldBeTrueWhenJustOneValuePresent() {
        assertTrue(isJustOneInputValuePresent("", "value2"));
    }

    @Test
    public void shouldBeFalseWhenIsSmaller() {
        assertFalse(isInputGreaterOrEqualThanThreshold("2.65", 2.66f));
    }

    @Test
    public void shouldBeFalseWhenNull() {
        assertFalse(isInputGreaterOrEqualThanThreshold(null, 2.66f));
    }

    @Test
    public void shouldBeFalseWhenHostNull() {
        assertFalse(isValidHost(null));
    }

    @Test
    public void shouldBeFalseWhenHostEmpty() {
        assertFalse(isValidHost(""));
    }

    @Test
    public void shouldBeFalseWhenHostBlank() {
        assertFalse(isValidHost("         "));
    }

    @Test
    public void shouldBeFalseWhenHostInvalid() {
        assertFalse(isValidHost("exam}ple.com"));
    }
}
