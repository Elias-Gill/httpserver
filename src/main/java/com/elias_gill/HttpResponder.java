package com.elias_gill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpResponder implements Runnable {
    Socket sock;

    HttpResponder(Socket sock) {
        this.sock = sock;
    }

    private boolean parseHeaders(BufferedReader reader) {
        try {

            String line = reader.readLine();
            while (line != "\r\n") {
                System.out.println(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void run() {
        try {
            // OutputStream out = this.sock.getOutputStream();
            InputStream in = this.sock.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            parseHeaders(reader);

            reader.close();
            this.sock.close();

        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

}
