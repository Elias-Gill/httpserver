package com.elias_gill.HttpProtocol;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private PrintWriter output;

    private String version;
    private int status;

    private Map<String, String> headers;
    private String body;

    /**
     * Generates a new httpResponse which has a default status 400 (bad request)
     */
    public HttpResponse(OutputStream out) {
        this.version = "1.1";
        this.status = 400;
        this.headers = new HashMap<String, String>();
        this.output = new PrintWriter(out);
        this.body = "";
    }

    /**
     * Sends the request to the client
     */
    public void send() {
        String heads = "";
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            heads += String.format("\r\n%s: %s", entry.getKey(), entry.getValue());
        }

        String msg = String.format(
                "HTTP/%s %d%s%s",
                this.version, this.status,
                heads,
                this.body);

        this.output.write(msg);
        this.output.flush();

        System.out.println(msg);
    }

    public void setHeader(String header, String value) {
        this.headers.put(header, value);
    }

    public HttpResponse withStatus(int status) {
        this.status = status;
        return this;
    }

    public HttpResponse withBody(String body) {
        this.body = "\r\n\r\n" + body;
        this.setHeader("Content-Length", String.format("%d", body.length()));
        return this;

    }
}
