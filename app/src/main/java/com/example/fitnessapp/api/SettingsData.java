package com.example.fitnessapp.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsData {

  @SerializedName("health_information")
  @Expose
  private HealthInfo healthInformation;
  @SerializedName("chat_settings")
  @Expose
  private ChatInfo chatSettings;
  @SerializedName("_id")
  @Expose
  private String id;
  @SerializedName("user")
  @Expose
  private String user;
  @SerializedName("__v")
  @Expose
  private Integer v;

  public HealthInfo getHealthInformation() {
    return healthInformation;
  }

  public void setHealthInformation(HealthInfo healthInformation) {
    this.healthInformation = healthInformation;
  }

  public ChatInfo getChatSettings() {
    return chatSettings;
  }

  public void setChatSettings(ChatInfo chatSettings) {
    this.chatSettings = chatSettings;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Integer getV() {
    return v;
  }

  public void setV(Integer v) {
    this.v = v;
  }

}