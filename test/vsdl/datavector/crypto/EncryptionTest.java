package vsdl.datavector.crypto;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static vsdl.datavector.crypto.Encryption.*;

import static vsdl.datavector.crypto.CryptoUtilities.*;

public class EncryptionTest {
    @Test
    public void testEncryptDecrypt() {
        final BigInteger test = fromAlphaNumeric("1234567890abcdefghijklmnopqrstuvwxyz");
        final BigInteger shortKey = randomKey(16);
        final BigInteger longKey = randomKey(128);
        BigInteger shortCipherText = encryptDecrypt(shortKey, test);
        BigInteger longCipherText = encryptDecrypt(longKey, test);
        assert !shortCipherText.equals(longCipherText);
        assert test.equals(encryptDecrypt(shortKey, shortCipherText));
        assert test.equals(encryptDecrypt(longKey, longCipherText));
    }
}
