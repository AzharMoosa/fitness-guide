package com.example.fitnessapp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExerciseData {

  @SerializedName("_id")
  @Expose
  private String id;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("sets")
  @Expose
  private Integer sets;

  @SerializedName("repetitions")
  @Expose
  private Integer repetitions;

  @SerializedName("__v")
  @Expose
  private Integer v;

  public ExerciseData(String name, Integer sets, Integer repetitions) {
    this.name = name;
    this.sets = sets;
    this.repetitions = repetitions;
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

  public Integer getSets() {
    return sets;
  }

  public void setSets(Integer sets) {
    this.sets = sets;
  }

  public Integer getRepetitions() {
    return repetitions;
  }

  public void setRepetitions(Integer repetitions) {
    this.repetitions = repetitions;
  }

  public Integer getV() {
    return v;
  }

  public void setV(Integer v) {
    this.v = v;
  }
}
