package com.marcel.Lanchonete.error;

import java.time.LocalDateTime;

import com.marcel.Lanchonete.enums.DetailType;

public class MasterDetails {
    private String title;
    private String message;
    private DetailType type;
    private LocalDateTime timestamp;

    public MasterDetails() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailType getType() {
        return type;
    }

    public void setType(DetailType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static final class Builder {
        private String title;
        private String message;
        private DetailType type;
        private LocalDateTime timestamp;

        private Builder() {
            
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder type(DetailType type) {
            this.type = type;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MasterDetails build() {
            MasterDetails masterErrorDetails = new MasterDetails();
            masterErrorDetails.setTitle(title);
            masterErrorDetails.setMessage(message);
            masterErrorDetails.setType(type);
            masterErrorDetails.setTimestamp(timestamp);
            return masterErrorDetails;
        }
    }
}
