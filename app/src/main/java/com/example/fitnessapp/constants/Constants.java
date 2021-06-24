package com.example.fitnessapp.constants;

import com.example.fitnessapp.user.User;

public class Constants {

    // API URL
    public static final String API_URL = "https://fitness-application-api.herokuapp.com/api/";
    // JSON Parameters
    public static final String TOKEN = "token";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    // Routes
    public static final String AUTH = "auth";
    public static final String USERS = "users";
    // User
    public static User user = new User("John Doe", "jdoe@gmail.com");
}
