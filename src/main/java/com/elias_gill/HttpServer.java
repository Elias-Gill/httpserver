package com.elias_gill;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.HttpProtocol.HttpResponse;
import com.elias_gill.contract.HttpRoute;
import com.elias_gill.parser.HttpParser;

public class HttpServer {
    private Executor threadPool;
    private int port;
    private final Map<String, HttpRoute> routes;

    // Default server
    private HttpServer() {
        this.port = 8080;
        this.routes = new HashMap<String, HttpRoute>();
        this.threadPool = Executors.newFixedThreadPool(100);
    }

    public void registerRoute(final String path, final HttpRoute route) {
        routes.put(path, route);
    }

    public void startServer() {
        System.out.println("Starting server...\n");
        try {
            ServerSocket server = new ServerSocket(this.port);
            while (true) {
                Socket sock = server.accept();
                this.threadPool.execute(new HttpConnection(this.routes, sock));
            }

        } catch (Exception e) {
            System.out.println("Cannot start server: " + e);
        }
    }

    /**
     * Handles the socket connection
     */
    private class HttpConnection implements Runnable {
        private Socket sock;
        private Map<String, HttpRoute> routes;

        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";

        public HttpConnection(Map<String, HttpRoute> routes, Socket sock) {
            this.sock = sock;
            this.routes = routes;
        }

        @Override
        public void run() {
            try {
                InputStream in = sock.getInputStream();
                OutputStream out = sock.getOutputStream();

                System.out.println(ANSI_GREEN + "-- Request --" + ANSI_RESET);

                HttpRequest req = HttpParser.parse(in);
                HttpResponse resp = new HttpResponse(out);


                System.out.println(ANSI_YELLOW + "-- Response --" + ANSI_RESET);

                HttpRoute route = this.routes.get(req.getPath());
                if (route == null) {
                    resp.withStatus(404).send();
                    return;
                }

                route.handle(req, resp);
                this.sock.close();

            } catch (Exception e) {
                System.out.println("Connection error: " + e);
            }
        }

    }

    /**
     * Default values for the builder are:
     * <ul>
     * <li>port: 8080</li>
     * <li>max connections: 100</li>
     * </ul>
     */
    public static class Builder {
        HttpServer server;

        Builder() {
            this.server = new HttpServer();
        }

        public HttpServer build() {
            return this.server;
        }

        public Builder withMaxConnections(final int maxConnections) {
            this.server.threadPool = Executors.newFixedThreadPool(maxConnections);
            return this;
        }

        public Builder withPort(final int port) {
            this.server.port = port;
            return this;
        }
    }

}
