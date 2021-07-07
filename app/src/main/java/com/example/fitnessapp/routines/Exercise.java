package com.example.fitnessapp.routines;

public class Exercise {

  private String day;
  private String name;
  private int sets;
  private int repetitions;

  public Exercise(String day) {
    this.day = day;
  }

  public Exercise(String day, String name, int sets, int repetitions) {
    this.day = day;
    this.name = name;
    this.sets = sets;
    this.repetitions = repetitions;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSets() {
    return sets;
  }

  public void setSets(int sets) {
    this.sets = sets;
  }

  public int getRepetitions() {
    return repetitions;
  }

  public void setRepetitions(int repetitions) {
    this.repetitions = repetitions;
  }

  public String formattedText() {
    if (name == null) {
      return day;
    }
    return name + " (" + sets + " x " + repetitions + ")";
  }

  @Override
  public String toString() {
    return day + " - " + name + " (" + sets + " x " + repetitions + ")";
  }
}
