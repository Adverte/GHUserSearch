package com.example.officepcdell.githubusersearch.classes;

public class APIError {

    private int statusCode;
    private String message;

    public APIError() {
    }

    public int statusCode() {
        return statusCode;
    }
    public String message() {
        return message;
    }
}