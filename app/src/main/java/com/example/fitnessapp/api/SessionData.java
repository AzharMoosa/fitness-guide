package com.example.fitnessapp.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionData {

  @SerializedName("exercises")
  @Expose
  private List<String> exercises = null;

  @SerializedName("_id")
  @Expose
  private String id;

  @SerializedName("day")
  @Expose
  private String day;

  @SerializedName("user")
  @Expose
  private String user;

  @SerializedName("__v")
  @Expose
  private Integer v;

  public SessionData(String day, List<String> exercises) {
    this.day = day;
    this.exercises = exercises;
  }

  public List<String> getExercises() {
    return exercises;
  }

  public void setExercises(List<String> exercises) {
    this.exercises = exercises;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Integer getV() {
    return v;
  }

  public void setV(Integer v) {
    this.v = v;
  }
}
