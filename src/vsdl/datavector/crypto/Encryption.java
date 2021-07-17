package vsdl.datavector.crypto;

import java.math.BigInteger;

import static vsdl.datavector.crypto.CryptoUtilities.*;

public class Encryption {

    public static String hash(String alphaNumericString) {
        checkAlphaNumeric(alphaNumericString);
        return ""; //todo
    }

    //todo - update this to ensure the result is alphanumeric, or it will not be transmittable!
    public static BigInteger encryptDecrypt(BigInteger sharedSecretKey, BigInteger plainTextMessage) {
        return sharedSecretKey.xor(plainTextMessage);
    }
}
