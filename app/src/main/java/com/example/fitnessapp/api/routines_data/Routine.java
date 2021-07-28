package com.example.fitnessapp.api.routines_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Routine {

  @SerializedName("id")
  @Expose
  private String id;

  public Routine(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
