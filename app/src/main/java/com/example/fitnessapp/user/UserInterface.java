package com.example.fitnessapp.user;

public interface UserInterface {
    void setName(String name);
    void setEmail(String email);
    void setPassword(String password);
    String getName();
    String getEmail();
    void setToken(String token);
}
