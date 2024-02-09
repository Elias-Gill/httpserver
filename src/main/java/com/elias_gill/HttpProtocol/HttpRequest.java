package com.elias_gill.HttpProtocol;

import java.io.PrintWriter;
import java.util.Map;

import com.elias_gill.contract.RequestType;

public class HttpRequest {
    private PrintWriter output;

    public String path;
    public String version;
    public RequestType.Type type;

    private Map<String, String> headers;
    private String body;

    public HttpRequest(PrintWriter out) {
        this.output = out;
    }

    public void writeResponse(HttpResponse response) {
        this.output.write(response.getResponse());
    }
}
