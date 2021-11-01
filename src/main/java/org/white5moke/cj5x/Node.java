package org.white5moke.cj5x;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bouncycastle.util.encoders.Hex;
import org.white5moke.cj5x.spark.CorsFilter;
import spark.Spark;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public class Node {
    private String host;
    private int port;
    private String name;
    private Wallet wallet = null;

    public Node() {}

    public void init(String host, int port, String name) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        setHost(host);
        setPort(port);
        setName(name);

        //run();
    }

    private void run() {
        Spark.port(getPort());
        Spark.threadPool(8, 2, 10000);

        // allow allow through CORS policies
        CorsFilter.apply();

        // base endpoint
        Spark.get("/", (req, res) -> {
            Gson g = new Gson();

            return g.toJson("Value is soul");
        });

        Spark.post("/tx", (req, res) -> {
            // TODO does Node have a wallet?
            // no > make one, and assign to Node instance
            // yes > assign to Node instance

            JsonObject payload = new Gson().fromJson(req.body(), JsonObject.class);

            // TODO need to get wallet string passed to private key for from
            String from = payload.get("from").getAsString();
            String to = payload.get("to").getAsString();
            byte[] data = payload.get("data").getAsBigInteger().toByteArray();
            double amount = payload.get("amount").getAsDouble();

            return "";
        });
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
