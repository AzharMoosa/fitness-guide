package com.example.fitnessapp.routines;

import java.util.List;

public class Session {
  private String day;
  private List<Exercise> exercises;

  public Session(String day, List<Exercise> exercises) {
    this.day = day;
    this.exercises = exercises;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public List<Exercise> getExercises() {
    return exercises;
  }

  public void setExercises(List<Exercise> exercises) {
    this.exercises = exercises;
  }
}
