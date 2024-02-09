package com.elias_gill.HttpProtocol;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private PrintWriter output;

    public String version;
    public int status;

    private Map<String, String> headers;
    public String body;

    private HttpResponse() {
    }

    public void setHeader(String header, String value) {
        this.headers.put(header, value);
    }

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

        System.out.println(msg);

        this.output.write(msg);
        this.output.flush();
    }

    public static class Builder {
        private HttpResponse resp;

        public Builder(OutputStream out) {
            this.resp = new HttpResponse();
            this.resp.version = "1.1";
            this.resp.headers = new HashMap<String, String>();
            this.resp.output = new PrintWriter(out);
        }

        public HttpResponse build() {
            return this.resp;
        }

        public Builder withStatus(int status) {
            this.resp.status = status;
            return this;
        }

        public Builder withBody(String body) {
            this.resp.body = "\r\n\r\n" + body;
            this.resp.setHeader("Content-Length", String.format("%d", body.length()));
            return this;

        }

        public Builder withHeaders(Map<String, String> headers) {
            this.resp.headers = headers;
            return this;
        }
    }
}
