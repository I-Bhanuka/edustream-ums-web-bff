package com.example.edustream_bff.exception;

public class ConflictException extends ApplicationException {
    public ConflictException(String message) {
        super(message, 409);
    }
}
