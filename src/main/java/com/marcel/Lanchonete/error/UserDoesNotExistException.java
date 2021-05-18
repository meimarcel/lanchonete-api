package com.marcel.Lanchonete.error;

public class UserDoesNotExistException extends RuntimeException {
    
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
