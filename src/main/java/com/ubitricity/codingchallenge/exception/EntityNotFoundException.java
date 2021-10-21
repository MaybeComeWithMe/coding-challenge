package com.ubitricity.codingchallenge.exception;


public class EntityNotFoundException extends RuntimeException {

    public static final String DEFAULT_MSG = "Entity not found";

    public EntityNotFoundException() {
        super(DEFAULT_MSG);
    }

}
