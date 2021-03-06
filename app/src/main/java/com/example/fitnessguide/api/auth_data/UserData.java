package com.example.fitnessguide.api.auth_data;

public class UserData {

  private String id;
  private String name;
  private String email;
  private String dateRegistered;
  private String token;

  public UserData(String id, String name, String email, String dateRegistered, String token) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.dateRegistered = dateRegistered;
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDateRegistered() {
    return dateRegistered;
  }

  public void setDateRegistered(String dateRegistered) {
    this.dateRegistered = dateRegistered;
  }
}
