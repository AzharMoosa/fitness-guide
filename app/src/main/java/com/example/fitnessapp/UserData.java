package com.example.fitnessapp;

public class UserData {

  private String id;
  private String name;
  private String email;
  private String dateRegistered;

  public UserData(String id, String name, String email, String dateRegistered) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.dateRegistered = dateRegistered;
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
