package org.white5moke.cj5x.transaction;

import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;
import org.white5moke.cj5x.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class TxOutput {
    public String id;
    public PublicKey recipient; // AKA the new owner of these coins
    public float value; // the amount of coins for new owner
    public String parentTransactionId; // the ID of the transaction in which this output was created

    public TxOutput(PublicKey recipient, float value, String parentTransactionId) throws
            IOException, NoSuchAlgorithmException {
        setRecipient(recipient);
        setValue(value);
        setParentTransactionId(parentTransactionId);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(getRecipient().getEncoded());
        stream.write(ByteBuffer.allocate(4).putFloat(getValue()).array());
        stream.write(getParentTransactionId().getBytes(StandardCharsets.UTF_8));
        byte[] id = Utility.SHA256(stream.toByteArray());
        setId(Hex.toHexString(id));
    }

    public boolean isMine(PublicKey publicKey) {
        return (publicKey == getRecipient());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public void setRecipient(PublicKey recipient) {
        this.recipient = recipient;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }
}
