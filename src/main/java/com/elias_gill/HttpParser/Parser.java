package com.elias_gill.HttpParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.contract.RequestType;

public class Parser {
    public static HttpRequest parseRequest(InputStream in, OutputStream out) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest req = new HttpRequest(new PrintWriter(out));

        parseInfo(req, inputReader);

        return req;
    }

    private static void parseInfo(HttpRequest req, BufferedReader in) {
        try {
            String line = in.readLine();
            String[] parts = line.split(" ");

            if (parts.length != 3) {
                // TODO: malformed request error
            }

            req.version = parts[2];
            req.path = parts[1];
            req.type = RequestType.strToType(parts[0]);

        } catch (Exception e) {
        }
    }
}
