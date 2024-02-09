package com.elias_gill.HttpParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.contract.RequestType;

public class Parser {
    public static HttpRequest parseRequest(InputStream in) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
        HttpRequest req = new HttpRequest();

        parseInfo(req, inputReader);
        parseHeaders(req, inputReader);

        // parse body
        int size = Integer.parseInt(req.getHeader("content-length"));
        try {
            String body = "";
            System.out.println(body);

            // is 1 because the content-length does not count the trailing character at the
            // start
            while (size > 1) {
                String line = inputReader.readLine();
                body += line;
                size = size - line.length();
                System.out.println(body);
            }

            req.setBody(body);

        } catch (Exception e) {
            System.out.println(e);
        }

        return req;
    }

    private static void parseHeaders(HttpRequest req, BufferedReader in) {
        try {
            String line = in.readLine();

            while (!line.isBlank()) {
                int colon = line.indexOf(":");

                String header = line.substring(0, colon);
                String value = "";

                if (line.length() > colon + 1) {
                    value = line.substring(colon + 1);
                }

                req.setHeader(header.trim().toLowerCase(), value.trim());
                line = in.readLine();
            }
        } catch (Exception e) {
        }
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
