package com.elias_gill.contract;

public class RequestType {
    public static Type strToType(String t) {
        switch (t.toLowerCase()) {
            case "get":
                return Type.GET;
            case "post":
                return Type.POST;
            case "put":
                return Type.PUT;
            case "delete":
                return Type.DELETE;

            default:
                return Type.INVALID;
        }
    }

    public enum Type {
        GET,
        POST,
        PUT,
        DELETE,
        INVALID,
    }
}
