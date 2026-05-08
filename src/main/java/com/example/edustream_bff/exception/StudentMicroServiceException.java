package com.example.edustream_bff.exception;

public class StudentMicroServiceException extends BFFApplicationException {
    public StudentMicroServiceException(String message, int statusCode) {
        super(message, statusCode);
    }
}
