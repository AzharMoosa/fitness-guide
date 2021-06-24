package com.example.fitnessapp.user;

public class User implements UserInterface {

    private String name;
    private String email;
    private String token;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
