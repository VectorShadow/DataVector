package vsdl.datavector.crypto;

import org.junit.jupiter.api.Test;

import static vsdl.datavector.crypto.Encryption.*;

public class EncryptionTest {
    @Test
    public void testEncryptDecrypt() {
        final String test = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()-=+~\\/";
        final String shortKey = "shortTestKey";
        final String longKey =
                "ejkehl37bjdbflwylr83lanjkasbdGHD&#sll2jjbjHdkjy3hkuhvk2yBGYGKJ7yedshgdjk636kHGD^KDY^^DJD^DJGGKD^^D^DF";
        String shortCipherText = encryptDecrypt(shortKey, test);
        String longCipherText = encryptDecrypt(longKey, test);
        assert shortCipherText.length() == longCipherText.length();
        assert !shortCipherText.equals(longCipherText);
        assert test.equals(encryptDecrypt(shortKey, shortCipherText));
        assert test.equals(encryptDecrypt(longKey, longCipherText));

    }
}
