package com.se3.ase.network;

public class ApiResponseHolder {
    private String body;
    private int statusCode;

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
