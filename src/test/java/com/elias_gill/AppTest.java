package com.elias_gill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.elias_gill.HttpProtocol.HttpRequest;
import com.elias_gill.contract.RequestType;
import com.elias_gill.parser.HttpParser;

class HttpParserTest {
    @BeforeAll
    static void setUp() {

    }

    @Test
    @DisplayName("Test http request parser")
    void testParser() {
        String msg = "GET / HTTP/1.1\r\n" +
                "content-length: 13\r\n" +
                "content-type: html/txt\r\n" +
                "\r\nhola que hace";

        InputStream in = new ByteArrayInputStream(msg.getBytes());
        HttpRequest req = HttpParser.parse(in);

        assertEquals("/", req.getPath());
        assertEquals(RequestType.Type.GET, req.getType());
        assertEquals("hola que hace", req.getBody());
        assertEquals("HTTP/1.1", req.getVersion());

        String contLenght = req.getHeader("CONTENT-LENGTH");
        assertEquals("13", contLenght);

        String contType = req.getHeader("CONTENT-TYPE");
        assertEquals("html/txt", contType);
    }
}
