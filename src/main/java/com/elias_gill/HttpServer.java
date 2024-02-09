package com.elias_gill;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.elias_gill.HttpParser.Parser;
import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.HttpProtocol.HttpResponse;
import com.elias_gill.HttpProtocol.HttpResponse.Builder;

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
        System.out.println("\nStarting HTTP server...\n");

        try (ServerSocket sockServer = new ServerSocket(this.port)) {
            while (true) {
                // tcp server socket
                final Socket sock = sockServer.accept();

                // passing the routes table to the new HttpConnection
                this.threadPool.execute(
                        new HttpConnection(sock, this.routes));
            }

        } catch (final IOException e) {
            System.out.println("Cannot start http server: \n" + e.toString());
            System.exit(1);
        }
    }

    /**
     * The http connection handler class.
     * Implements Runnable to execute an httpRoute on this socket
     */
    private class HttpConnection implements Runnable {
        Socket sock;
        Map<String, HttpRoute> routes;

        public HttpConnection(final Socket sock, final Map<String, HttpRoute> routes) {
            this.sock = sock;
            this.routes = routes;
        }

        public void run() {
            try {
                final InputStream in = this.sock.getInputStream();
                final OutputStream out = this.sock.getOutputStream();

                // parse and extract the route
                final HttpRequest req = Parser.parseRequest(in);
                final HttpRoute route = this.routes.get(req.path);

                if (route != null) {
                    route.Handle(req, out);
                } else {
                    // TODO: 404 "page not found" response
                    return;
                }

                this.sock.close();

            } catch (final IOException e) {
                System.out.println("I/O error: " + e.getMessage());
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
