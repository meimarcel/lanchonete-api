package com.marcel.Lanchonete.error;

public class UserAlreadyExistException extends RuntimeException {
    
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
