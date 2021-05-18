package com.marcel.Lanchonete.error;

public class PasswordNotMatchingException extends RuntimeException {
    
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
