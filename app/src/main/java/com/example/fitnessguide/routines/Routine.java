package com.example.fitnessguide.routines;

import java.util.List;

public class Routine {
  private String name;
  private boolean isActive;
  private List<Session> routines;

  public Routine(String name, boolean isActive, List<Session> routines) {
    this.name = name;
    this.isActive = isActive;
    this.routines = routines;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public List<Session> getRoutines() {
    return routines;
  }

  public void setRoutines(List<Session> routines) {
    this.routines = routines;
  }
}
