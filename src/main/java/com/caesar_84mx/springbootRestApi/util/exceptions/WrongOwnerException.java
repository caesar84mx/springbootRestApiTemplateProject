package com.caesar_84mx.springbootRestApi.util.exceptions;

public class WrongOwnerException extends RuntimeException {
    public WrongOwnerException(String message) {
        super(message);
    }
}
