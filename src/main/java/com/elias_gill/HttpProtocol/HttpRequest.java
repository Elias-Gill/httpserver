package com.elias_gill.HttpProtocol;

import java.util.HashMap;
import java.util.Map;

import com.elias_gill.contract.RequestType;

public class HttpRequest {
    public String path;
    public String version;
    public RequestType.Type type;

    private Map<String, String> headers;
    private String body;

    public HttpRequest() {
        this.headers = new HashMap<String, String>();
    }

    public String getHeader(String header) {
        return headers.get(header.toLowerCase());
    }

    public void setHeader(String header, String value) {
        this.headers.put(header.toLowerCase(), value);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
