package com.example.edustream_bff.exception;

public class BFFApplicationException extends RuntimeException {

    private final int statusCode;

    public BFFApplicationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
