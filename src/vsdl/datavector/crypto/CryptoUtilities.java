package vsdl.datavector.crypto;

import java.math.BigInteger;

public class CryptoUtilities {

    private static final int DIGIT_0 = '0';
    private static final int DIGIT_9 = '9';
    private static final int ALPHA_A = 'A';
    private static final int ALPHA_Z = 'Z';
    private static final int ALPHA_a = 'a';
    private static final int ALPHA_z = 'z';

    public static boolean isAlphaNumeric(char c) {
        return ((c >= DIGIT_0 && c <= DIGIT_9) || (c >= ALPHA_A && c <= ALPHA_Z) || (c >= ALPHA_a && c <= ALPHA_z));
    }

    public static boolean isAlphaNumeric(String s) {
        for (Character c : s.toCharArray()) {
            if (!isAlphaNumeric(c)) return false;
        }
        return true;
    }

    public static BigInteger fromAlphaNumeric(String alphaNumericString) {
        if (!isAlphaNumeric(alphaNumericString)) {
            throw new IllegalArgumentException(
                    "String " + alphaNumericString + " contained non-alphanumeric characters."
            );
        }
        if (alphaNumericString.charAt(0) == '0') {
            throw new IllegalArgumentException("Input string must not contain leading 0s.");
        }
        return new BigInteger(alphaNumericString.toLowerCase(), Character.MAX_RADIX);
    }

    public static String toAlphaNumeric(BigInteger bigInteger) {
        return bigInteger.toString(Character.MAX_RADIX);
    }

}
