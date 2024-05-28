package com.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlayerExistsAlreadyAdvice {

    @ExceptionHandler(PlayerExistsAlreadyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String PlayerExistsAlreadyHandler(PlayerExistsAlreadyException ex) {
        return ex.getMessage();
    }
}
