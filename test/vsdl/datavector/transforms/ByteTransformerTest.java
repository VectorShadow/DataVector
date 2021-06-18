package vsdl.datavector.transforms;

import org.junit.jupiter.api.Test;

import static vsdl.datavector.transforms.ByteTransformer.*;

public class ByteTransformerTest {

    @Test
    public void testEndToEnd() {
        String in =
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        int l = in.length();
        byte[] b0 = new byte[l];
        byte[] b1 = new byte[l];
        String out = "";
        for (int i = 0; i < l; ++i) {
            b0[i] = fromChar(in.charAt(i));
            out += toChar(b0[i]);
            b1[i] = fromChar(out.charAt(i));
        }
        for (int i = 0; i < b0.length; ++i) {
            assert in.charAt(i) == out.charAt(i);
            assert b0[i] == b1[i];
        }
    }
}
