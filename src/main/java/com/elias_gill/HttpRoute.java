package com.elias_gill;

import java.util.Map;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.contract.HttpHandler;
import com.elias_gill.contract.RequestType;

public class HttpRoute {
    private Map<RequestType.Type, HttpHandler> handlers;

    final void Handle(final HttpRequest req) {
        final HttpHandler handler = handlers.get(req.type);

        if (handler == null) {
            // TODO: 404
            return;
        }

        handler.Handle(req);
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
