package com.elias_gill;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* 
 * The main function of our program start a multithreaded tcp server 
 * 
 */
public class App {
    public static void main(final String[] args) {
        System.out.println("\nStarting TCP server...\n");
        // threadpoool of http responders
        final Executor threadPool = Executors.newFixedThreadPool(100);

        try (ServerSocket sockServer = new ServerSocket(8080)) {
            while (true) {
                // tcp server socket
                final Socket sock = sockServer.accept();
                threadPool.execute(new HttpResponder(sock));
            }
        } catch (final IOException e) {
            System.out.println("Cannot start tcp server: " + e.toString());
            System.exit(1);
        }
    }
}
