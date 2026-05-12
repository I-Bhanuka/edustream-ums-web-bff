package com.example.edustream_bff.exception;

public class SagaException extends BFFApplicationException{
    public SagaException(String message, int statusCode) {
        super(message, statusCode);
    }

    public SagaException(String message, int statusCode, String downStreamMessage) {
        super(message, statusCode, downStreamMessage);
    }
}
