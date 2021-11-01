package org.white5moke.cj5x.spark;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;

public final class CorsFilter {
    private static final HashMap<String, String> headers = new HashMap<String, String>();

    static {
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Headers", "*");
    };

    public final static void apply() {
        Filter f = new Filter() {
            @Override
            public void handle(Request request, Response response) {
                headers.forEach((k, v) -> {
                    response.header(k, v);
                });
            }
        };

        Spark.options("/*", (req, res) -> {
            headers.forEach((k, v) -> {
                String a = req.headers(k);

                if(a != null) {
                    res.header(k, v);
                }
            });

            return "OK";
        });

        Spark.before(f);
    }
}
