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

        server.registerRoute("/transacciones", new HttpRoute()
                .Get(new algo())
                .Post(new algo2()));

        server.startServer();
    }

    private static class algo implements HttpHandler {
        @Override
        public void Handle(HttpRequest req, HttpResponse.Builder builder) {
            builder
                    .withBody("Funciona al parecer")
                    .withStatus(200)
                    .build().send();
        }
    }

    private static class algo2 implements HttpHandler {
        @Override
        public void Handle(HttpRequest req, HttpResponse.Builder builder) {
            builder
                    .withBody(req.getBody())
                    .withStatus(200)
                    .build().send();
        }
    }
}
