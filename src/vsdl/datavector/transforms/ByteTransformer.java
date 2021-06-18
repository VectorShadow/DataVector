package vsdl.datavector.transforms;

public class ByteTransformer {

    public static byte fromChar(char c) {
        return (byte)(0x007f & c);
    }

    public static char toChar(byte b) {
        return (char)(0x007f & b);
    }
}
