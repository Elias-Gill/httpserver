package com.elias_gill.HttpProtocol;

import java.util.HashMap;
import java.util.Map;

import com.elias_gill.contract.RequestType;

public class HttpRequest {
    private String path;
    private String version;
    private RequestType.Type type;

    private Map<String, String> headers;
    private String body;

    public HttpRequest() {
        this.headers = new HashMap<String, String>();
        this.body = "";
    }

    // -- Setters and getters -- 

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RequestType.Type getType() {
        return type;
    }

    public void setType(RequestType.Type type) {
        this.type = type;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
