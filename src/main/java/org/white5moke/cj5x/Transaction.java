package org.white5moke.cj5x;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import org.white5moke.cj5x.signing.Signer;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.util.Base64;

public class Transaction {
    private Wallet from;
    private PublicKey to;
    private float amount;
    private byte[] data;
    private long created = Instant.now().toEpochMilli();
    private byte[] signature = null;

    /**
     * Transaction [tx]
     * @param from String Public address
     * @param to String Public address
     * @param amount double Amount of transaction
     * @param data byte[] Random data
     */
    public Transaction(Wallet from, PublicKey to, float amount, byte[] data) {
        setFrom(from);
        setTo(to);
        setAmount(amount);
        setData(data);
        setCreated(Instant.now().toEpochMilli());

        try {
            byte[] sig = Signer.ECDSASignature(getFrom().getKeyPair().getPrivate(), this.toString());
            setSignature(sig);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
    }

    public Transaction() {}

    public boolean process() {
        if(Signer.verifyECDSASignature())

        return false;
    }

    public Wallet getFrom() {
        return from;
    }

    public void setFrom(Wallet from) {
        this.from = from;
    }

    public PublicKey getTo() {
        return to;
    }

    public void setTo(PublicKey to) {
        this.to = to;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        JsonSerializer<Transaction> serializer = (src, typeOfSrc, context) -> {
            JsonObject j = new JsonObject();

            j.addProperty("amount", src.getAmount());
            j.addProperty("data", new String(src.getData(), StandardCharsets.UTF_8));
            j.addProperty("from", Base64.getEncoder().encodeToString(src.getFrom().getKeyPair().getPublic().getEncoded()));
            j.addProperty("to", Base64.getEncoder().encodeToString(src.getTo().getEncoded()));
            j.addProperty("created", src.getCreated());
            //if(getSignature() != null) j.addProperty("signature", Base64.getEncoder().encodeToString(getSignature()));

            return j;
        };

        Gson g = new GsonBuilder().registerTypeAdapter(Transaction.class, serializer)
                .disableHtmlEscaping().create();

        return g.toJson(this);
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
