package com.alura.liter_alura.exceptions;

public class AuthorNotFound extends RuntimeException{
    public AuthorNotFound(String message) {
        super(message);
    }
}
