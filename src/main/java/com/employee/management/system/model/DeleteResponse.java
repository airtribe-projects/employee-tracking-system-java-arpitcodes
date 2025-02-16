package com.employee.management.system.model;

public class DeleteResponse {
    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeleteResponse() {
    }

    public DeleteResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    private String message;
}
