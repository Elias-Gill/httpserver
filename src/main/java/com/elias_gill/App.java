package com.elias_gill;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.HttpProtocol.HttpResponse;
import com.elias_gill.contract.HttpRoute;

public class App {
    public static void main(String[] args) {

        HttpServer server = new HttpServer.Builder()
                .withPort(8080)
                .withMaxConnections(20)
                .build();

        server.registerRoute("/", new home());

        server.startServer();
    }

    private static class home extends HttpRoute {
        @Override
        public void GET(HttpRequest req, HttpResponse resp) {
            resp.withStatus(200)
                    .withBody(req.getBody())
                    .send();
        }

        @Override
        public void POST(HttpRequest req, HttpResponse resp) {
            resp.setHeader("content-type", "application/json");

            resp.withStatus(200)
                    .withBody("{\"nombre\":\"elias sebastian gill\"}")
                    .send();
        }
    }
}
