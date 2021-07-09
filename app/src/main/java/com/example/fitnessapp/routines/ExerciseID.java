package com.example.fitnessapp.routines;

public class ExerciseID {

  private String day;
  private String id;

  public ExerciseID(String day, String id) {
    this.day = day;
    this.id = id;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
