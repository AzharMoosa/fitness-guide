package com.example.fitnessapp.response;

public class Response implements ResponseInterface {

    private final String message;
    private final String token;

    public Response(String message, String token) {
        this.message = message;
        this.token = token;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getToken() {
        return token;
    }
}
