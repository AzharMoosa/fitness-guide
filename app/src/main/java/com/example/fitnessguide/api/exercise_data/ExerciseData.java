package com.example.fitnessguide.api.exercise_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ExerciseData implements Serializable {
  @SerializedName("_id")
  @Expose
  private String id;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("type")
  @Expose
  private String type;

  @SerializedName("description")
  @Expose
  private String description;

  @SerializedName("info")
  @Expose
  private String info;

  @SerializedName("date")
  @Expose
  private String date;

  @SerializedName("__v")
  @Expose
  private Integer v;

  public ExerciseData(String name, String type) {
    this.name = name;
    this.type = type;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Integer getV() {
    return v;
  }

  public void setV(Integer v) {
    this.v = v;
  }
}
