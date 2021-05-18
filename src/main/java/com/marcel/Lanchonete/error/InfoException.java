package com.marcel.Lanchonete.error;

import com.marcel.Lanchonete.enums.DetailType;

public class InfoException extends RuntimeException {
    
    private DetailType type;

    public InfoException(String message, DetailType type) {
        super(message);
        this.type = type;
    }

    public DetailType getType() {
        return type;
    }

    public void setType(DetailType type) {
        this.type = type;
    }

    
}
