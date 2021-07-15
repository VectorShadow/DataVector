package vsdl.datavector.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static vsdl.datavector.crypto.CryptoUtilities.*;

public class CryptoUtilitiesTest {

    @Test
    public void testIsAlphaNumericChar() {
        assert !isAlphaNumeric('!');
        assert !isAlphaNumeric('%');
        assert !isAlphaNumeric('#');
        assert !isAlphaNumeric('\n');
        assert !isAlphaNumeric('\t');
        assert !isAlphaNumeric(']');
        assert !isAlphaNumeric('-');
        assert !isAlphaNumeric('=');
        assert !isAlphaNumeric('&');
        assert !isAlphaNumeric('\\');
        assert !isAlphaNumeric('/');
    }

    @Test
    public void testIsAlphaNumericString() {
        checkAlphaNumeric("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        assertThrows(IllegalArgumentException.class, () -> checkAlphaNumeric("testP@$$w0r[)"));
    }

    @Test
    public void testToAndFromString() {
        final String testString = "1234abf339js39hdkajsk3hfhgsyjfgky3kgrfdkhk";
        assert testString.toLowerCase().equals(toAlphaNumeric(fromAlphaNumeric(testString)));
    }

    @Test
    public void testToStringExceptionNonAlphaNumeric() {
        assertThrows(IllegalArgumentException.class, () -> fromAlphaNumeric("?"));
    }

    @Test
    public void testToStringExceptionLeadingZero() {
        assertThrows(IllegalArgumentException.class, () -> fromAlphaNumeric("01"));
    }


}
