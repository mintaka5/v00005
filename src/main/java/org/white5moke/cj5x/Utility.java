package org.white5moke.cj5x;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Utility {
    public static byte[] RipeMD160(byte[] b) throws NoSuchAlgorithmException, NoSuchProviderException {
        return MessageDigest.getInstance("RipeMD160", "BC").digest(b);
    }

    public static byte[] SHA256(byte[] b) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(b);
    }

    public static void printStringDetails(String name, String s) {
        String d = name + ": [" + s.length() + "]: " + s;

        System.out.println(d);
    }
}
