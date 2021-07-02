package com.example.fitnessapp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RoutinesData {

  @SerializedName("isActive")
  @Expose
  private Boolean isActive;

  @SerializedName("routines")
  @Expose
  private List<Object> routines = null;

  @SerializedName("_id")
  @Expose
  private String id;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("user")
  @Expose
  private String user;

  @SerializedName("date")
  @Expose
  private String date;

  @SerializedName("__v")
  @Expose
  private Integer v;

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public List<Object> getRoutines() {
    return routines;
  }

  public void setRoutines(List<Object> routines) {
    this.routines = routines;
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

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
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
