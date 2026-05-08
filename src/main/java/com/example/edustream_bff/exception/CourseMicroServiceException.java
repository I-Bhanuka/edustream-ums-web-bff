package com.example.edustream_bff.exception;

public class CourseMicroServiceException extends BFFApplicationException {
    public CourseMicroServiceException(String message, int statusCode) {
        super(message, statusCode);
    }
}
