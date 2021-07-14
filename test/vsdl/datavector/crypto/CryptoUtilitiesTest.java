package vsdl.datavector.crypto;

import org.junit.jupiter.api.Test;

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
}
