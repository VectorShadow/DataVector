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
        assert isAlphaNumeric("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        assert !isAlphaNumeric("testP@$$w0r[)");
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
