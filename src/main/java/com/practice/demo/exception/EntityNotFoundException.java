package com.practice.demo.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message, String... values) {
        super(String.format(message, (Object[]) values));
    }

}
