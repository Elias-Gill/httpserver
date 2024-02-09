package com.elias_gill.contract;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.HttpProtocol.HttpResponse;

public interface HttpHandler {
    public HttpResponse Handle(HttpRequest req);
}
