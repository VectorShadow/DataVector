package vsdl.datavector.elements;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static vsdl.datavector.transforms.ByteTransformer.*;

public class DataMessage {

    public static final char HEADER = '{';
    public static final char SEPARATOR = '|';
    public static final char TRAILER = '}';

    public static final byte MIN_VAL = 0x20;
    public static final byte MAX_VAL = 0x7e;

    private final byte[] BYTES;
    private final String TEXT;

    public DataMessage(String messageText) {
        TEXT = messageText;
        BYTES = new byte[TEXT.length()];
        validate();
    }

    public byte[] getBytes() {
        return BYTES;
    }

    @Override
    public String toString() {
        return TEXT;
    }

    public ArrayList<String> toBlocks() {
        StringTokenizer stringTokenizer = new StringTokenizer(TEXT, "" + HEADER + SEPARATOR + TRAILER, false);
        ArrayList<String> blocks = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            blocks.add(stringTokenizer.nextToken());
        }
        return blocks;
    }

    public void validate() {
        for (int i = 0; i < TEXT.length(); ++i) {
            char c = TEXT.charAt(i);
            byte b = fromChar(c);
            if (b < MIN_VAL || b > MAX_VAL) {
                throw new IllegalArgumentException("Invalid input char: " + c);
            }
            BYTES[i] = b;
        }
    }
}
