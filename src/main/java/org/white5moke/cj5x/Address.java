package org.white5moke.cj5x;

import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.Base58;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.util.Arrays;

public class Address {
    public Address() {}

    public String generate(PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchProviderException {
        ECPublicKey key = (ECPublicKey) publicKey;
        ECPoint point = key.getW();
        BigInteger aX = point.getAffineX();
        BigInteger aY = point.getAffineY();

        byte[] merger = ArrayUtils.addAll(
                Arrays.copyOfRange(aX.toByteArray(), 0, 16),
                Arrays.copyOfRange(aY.toByteArray(), 16, 32)
        );
        //Utility.printStringDetails("merger", Hex.toHexString(merger));

        byte[] pub = new byte[33];
        pub[0] = (byte) 2;
        System.arraycopy(merger, 0, pub, 1, 32);
        //Utility.printStringDetails("pub", Hex.toHexString(pub));

        byte[] s1 = Utility.SHA256(pub);
        //Utility.printStringDetails("s1", Hex.toHexString(s1));

        byte[] r1 = Utility.RipeMD160(s1);
        //Utility.printStringDetails("r1", Hex.toHexString(r1));

        // add 0x00
        byte[] r2 = new byte[r1.length+1];
        r2[0] = (byte) 0xc;
        System.arraycopy(r1, 0, r2, 1, r1.length);
        //Utility.printStringDetails("r2", Hex.toHexString(r2));

        byte[] s3 = Utility.SHA256(Utility.SHA256(r2));

        byte[] alloc = new byte[25];
        System.arraycopy(r2, 0, alloc, 0, 21);
        System.arraycopy(s3, 0, alloc, 21, 4);
        //Utility.printStringDetails("alloc", Hex.toHexString(alloc));

        String address = Base58.encode(alloc);
        //Utility.printStringDetails("address", address);

        return address;
    }
}
