package vsdl.datavector.crypto;

import static vsdl.datavector.crypto.CryptoUtilities.*;

public class Encryption {

    public static String hash(String alphaNumericString) {
        checkAlphaNumeric(alphaNumericString);
        return ""; //todo
    }

    //todo - update this to ensure the result is alphanumeric, or it will not be transmittable!
    public static String encryptDecrypt(String sharedSecretKey, String plainTextMessage) {
        byte[] in = plainTextMessage.getBytes();
        byte[] key = sharedSecretKey.getBytes();
        int size = in.length;
        byte[] out = new byte[size];
        for (int i = 0; i < size; ++i) {
            out[i] = (byte)(in[i] ^ key[i % key.length]);
        }
        return new String(out);
    }
}
