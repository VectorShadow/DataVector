package vsdl.datavector.crypto;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

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

    @Test
    public void testRandomKey() {
        for (int i = 4; i < 257; i +=16) {
            BigInteger bi = randomKey(i);
            assert toAlphaNumeric(bi).length() == i;
        }
    }

    @Test
    public void testSaltAndHash() {
        final int count = 64;
        final String[] randomSalts = new String[count];
        final String[] randomPasswords = new String[count];
        final String[] randomHashes = new String[count];
        final String[] fixedPasswordHashes = new String[count];
        final String fixedPassword = "passw0rd";
        for (int i = 0; i < count; ++i) {
            randomSalts[i] = randomAlphaNumericString(8);
            randomPasswords[i] = randomAlphaNumericString(16);
            final String saltedRandomPassword = salt(randomPasswords[i], randomSalts[i]);
            final String saltedFixedPassword = salt(fixedPassword, randomSalts[i]);
            randomHashes[i] = hash(saltedRandomPassword);
            fixedPasswordHashes[i] = hash(saltedFixedPassword);
        }
        for (int i = 0; i < count; ++i) {
            for (int j = 0; j < count; ++j) {
                if (i == j) continue;
                assert !randomHashes[i].equals(randomHashes[j]);
                assert !fixedPasswordHashes[i].equals(fixedPasswordHashes[j]);
            }
        }
    }
}
