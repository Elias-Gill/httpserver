package com.elias_gill;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.HttpProtocol.HttpResponse;
import com.elias_gill.contract.HttpHandler;

public class App {
    public static void main(String[] args) {
        HttpServer server = new HttpServer.Builder()
                .withMaxConnections(20)
                .withPort(8080)
                .build();

        server.registerRoute("/", new HttpRoute()
                .Get(new algo())
                .Post(new algo()));

        server.startServer();
    }

    private static class algo implements HttpHandler {
        @Override
        public HttpResponse Handle(HttpRequest req) {
            System.out.println("funciona al parecer");
            return new HttpResponse();
        }
    }
}
