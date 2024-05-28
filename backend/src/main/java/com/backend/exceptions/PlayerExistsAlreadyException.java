package com.backend.exceptions;

public class PlayerExistsAlreadyException extends RuntimeException {
    public PlayerExistsAlreadyException(String name) {
        super("Player " + name + " already exists");
    }
}
