package org.white5moke.cj5x;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

/**
 * Wallet
 *
 * TODO
 * Private keys are the association with each wallet's balance/data
 * The public address derived from public key is the connection, so I
 * need a way to validate publickey against privatekey
 */
public class Wallet {
    private KeyPair keyPair;
    private long created = Instant.now().toEpochMilli();

    public Wallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("EC", "BC");

        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        gen.initialize(ecSpec);

        setCreated(Instant.now().toEpochMilli());

        setKeyPair(gen.generateKeyPair());
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    /**
     * Converts private key to WIF address
     * @return String Base58 encoded public address
     */
    public String address() throws NoSuchAlgorithmException, NoSuchProviderException {
        Address a = new Address();

        return a.generate(getKeyPair().getPublic());
    }

    public String toString() {
        JsonSerializer<Wallet> serializer = (src, typeOfSrc, context) -> {
            JsonObject j = new JsonObject();

            Instant i = Instant.ofEpochSecond(src.getCreated());
            Date date = Date.from(i);
            DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
            String created = dtf.print(date.getTime());

            JsonObject keys = new JsonObject();
            byte[] encodedPub = src.getKeyPair().getPublic().getEncoded();
            keys.addProperty("public", Base64.getEncoder().encodeToString(encodedPub));

            j.addProperty("created", getCreated());
            try {
                j.addProperty("address", src.address());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            j.add("keys", keys);

            return j;
        };

        Gson g = new GsonBuilder().registerTypeAdapter(Wallet.class, serializer)
                /*.setPrettyPrinting()*/.disableHtmlEscaping().create();

        return g.toJson(this);
    }
}
