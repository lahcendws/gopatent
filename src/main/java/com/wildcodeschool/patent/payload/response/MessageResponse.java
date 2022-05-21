package com.wildcodeschool.patent.payload.response;

public class MessageResponse {
    private String message;

    /**
     * messages which we can send on angular
     * @param message
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
