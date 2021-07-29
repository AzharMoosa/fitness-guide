package com.example.fitnessguide.api.settings_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthInfo {

  @SerializedName("date_of_birth")
  @Expose
  private String dateOfBirth;
  @SerializedName("gender")
  @Expose
  private String gender;
  @SerializedName("height")
  @Expose
  private Integer height;
  @SerializedName("weight")
  @Expose
  private Integer weight;

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

}