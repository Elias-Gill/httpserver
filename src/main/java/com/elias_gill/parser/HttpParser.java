package com.elias_gill.parser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.contract.RequestType;

public class HttpParser {
    public static HttpRequest parse(InputStream in) {
        HttpRequest req = new HttpRequest();
        try {
            // writte the entire msg into a buffer
            int size = in.available();
            char[] cbuf = new char[size];
            InputStreamReader inReader = new InputStreamReader(in);
            inReader.read(cbuf);

            // generate a scanner and parse the request
            Scanner sc = new Scanner(new String(cbuf));

            // -- parse request info -- 
            if (!sc.hasNextLine()) {
                // TODO: empty request
                return null;
            }

            String[] info = sc.nextLine().split(" ");
            req.setType(RequestType.strToType(info[0]));
            req.setPath(info[1]);
            req.setVersion(info[2]);

            // INFO: print reques info
            System.out.println(String.format("%s %s %s", req.getType(), req.getPath(), req.getVersion()));

            // -- parse headers -- 
            if (!sc.hasNextLine()) {
                return req;
            }

            String line = sc.nextLine();
            while (!line.isBlank()) {
                // INFO: print headers
                System.out.println(line);

                int colon = line.indexOf(":");
                // only add header if not empty
                if (colon + 1 < line.length()) {
                    req.setHeader(line.substring(0, colon), line.substring(colon + 1));
                }

                line = sc.nextLine();
            }

            // -- parse body -- 
            String body = "";
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                body += line;

                if (sc.hasNextLine()) {
                    body += "\n";
                }
            }

            // INFO: print body
            System.out.println("\n" + body);
            req.setBody(body);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return req;
    }
}
