package org.white5moke.cj5x.signing;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Signer {
    public static byte[] generateSignature(String plainText, KeyPair keys) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
        Signature sign = Signature.getInstance("SHA256withECDSA", "BC");
        sign.initSign(keys.getPrivate());
        sign.update(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] signature = sign.sign();

        return signature;
    }

    public static boolean validateSignature(String plainText, KeyPair pair, byte[] signature) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
        Signature verity = Signature.getInstance("SHA256withECDSA", "BC");
        verity.initVerify(pair.getPublic());
        verity.update(plainText.getBytes(StandardCharsets.UTF_8));

        return verity.verify(signature);
    }

    public static byte[] ECDSASignature(PrivateKey privKey, String in) throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature dsa = Signature.getInstance("ECDSA", "BC");
        dsa.initSign(privKey);
        dsa.update(in.getBytes(StandardCharsets.UTF_8));

        return dsa.sign();
    }

    public static boolean verifyECDSASignature(PublicKey pubKey, String in, byte[] signature) throws
            NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature verify = Signature.getInstance("ECDSA", "BC");
        verify.initVerify(pubKey);
        verify.update(in.getBytes(StandardCharsets.UTF_8));

        return verify.verify(signature);
    }
}
