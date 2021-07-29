package com.example.fitnessguide.routines;

import java.io.Serializable;
import java.util.List;

public class Session implements Serializable {
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
