package com.sistemaOficina.backend.dto;

public class ErrorResponse {
    private String message;
    private String timestamp;
    
    public ErrorResponse() {
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
    
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}