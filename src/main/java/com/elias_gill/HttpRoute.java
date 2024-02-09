package com.elias_gill;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.HttpProtocol.HttpResponse;
import com.elias_gill.contract.HttpHandler;
import com.elias_gill.contract.RequestType;

public class HttpRoute {
    private Map<RequestType.Type, HttpHandler> handlers;

    public HttpRoute() {
        this.handlers = new HashMap<RequestType.Type, HttpHandler>();
    }

    void Handle(final HttpRequest req, final OutputStream out) {
        final HttpHandler handler = handlers.get(req.type);

        if (handler == null) {
            // TODO: 404
            return;
        }

        handler.Handle(req, new HttpResponse.Builder(out));
    }

    public HttpRoute Get(final HttpHandler handler) {
        handlers.put(RequestType.Type.GET, handler);
        return this;
    }

    public HttpRoute Post(final HttpHandler handler) {
        handlers.put(RequestType.Type.POST, handler);
        return this;
    }

    public HttpRoute Delete(final HttpHandler handler) {
        handlers.put(RequestType.Type.DELETE, handler);
        return this;
    }

    public HttpRoute Put(final HttpHandler handler) {
        handlers.put(RequestType.Type.PUT, handler);
        return this;
    }
}
