package org.white5moke.cj5x;

import org.apache.commons.lang3.RandomUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.MnemonicUtils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CJ5X {
    public CJ5X() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        Node node = new Node();
        node.init("127.0.0.1", 8000, "m@nd0");

        ArrayList<Wallet> fromWallets = new ArrayList<Wallet>();
        for (int i : IntStream.rangeClosed(1, 3).toArray()) {
            Wallet w = new Wallet();
            /*Utility.printStringDetails(
                    "private key",
                    Base64.encodeBase64String(w.getKeyPair().getPrivate().getEncoded())
            );
            Utility.printStringDetails(
                    "public key",
                    Base64.encodeBase64String(w.getKeyPair().getPublic().getEncoded())
            );*/
            fromWallets.add(w);
        }

        ArrayList<Wallet> toWallets = new ArrayList<Wallet>();
        for (int i : IntStream.rangeClosed(1, 3).toArray()) {
            Wallet w = new Wallet();
            /*Utility.printStringDetails(
                    "private key",
                    Base64.encodeBase64String(w.getKeyPair().getPrivate().getEncoded())
            );
            Utility.printStringDetails(
                    "public key",
                    Base64.encodeBase64String(w.getKeyPair().getPublic().getEncoded())
            );*/
            toWallets.add(w);
        }

        IntStream.rangeClosed(0, 10).forEach((i) -> {
            Wallet wf = fromWallets.get(RandomUtils.nextInt(0, 2));
            Wallet wt = toWallets.get(RandomUtils.nextInt(0, 2));

            SecureRandom sr = new SecureRandom();

            String rw = MnemonicUtils.generateMnemonic(sr.generateSeed(16));

            Transaction tx = new Transaction(
                    wf,
                    wt.getKeyPair().getPublic(),
                    RandomUtils.nextFloat((float) 1.00, (float) 1000.00),
                    rw.getBytes(StandardCharsets.UTF_8)
            );
        });
    }

    public static void main(String[] args) throws Exception {
        new CJ5X();
    }
}
