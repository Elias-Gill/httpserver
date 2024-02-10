package com.elias_gill.contract;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.HttpProtocol.HttpResponse;

public abstract class HttpRoute implements HttpHandler {
    final public void handle(HttpRequest req, HttpResponse resp) {
        switch (req.getType()) {
            case GET:
                this.GET(req, resp);
                return;
            case POST:
                this.POST(req, resp);
                return;
            case PUT:
                this.PUT(req, resp);
                return;
            case DELETE:
                this.DELETE(req, resp);
                return;
            default:
                this.invalid(req, resp);
                return;
        }
    }

    public void GET(HttpRequest req, HttpResponse resp) {
        resp.send();
    }

    public void POST(HttpRequest req, HttpResponse resp) {
        resp.send();
    }

    public void DELETE(HttpRequest req, HttpResponse resp) {
        resp.send();
    }

    public void PUT(HttpRequest req, HttpResponse resp) {
        resp.send();
    }

    final private void invalid(HttpRequest req, HttpResponse resp) {
        resp.withStatus(400).send();
    }
}
