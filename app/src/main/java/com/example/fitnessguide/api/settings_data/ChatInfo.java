package com.example.fitnessguide.api.settings_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ChatInfo{

  @SerializedName("custom_name")
  @Expose
  private String customName;
  @SerializedName("isVisible")
  @Expose
  private Boolean isVisible;

  public String getCustomName() {
    return customName;
  }

  public void setCustomName(String customName) {
    this.customName = customName;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

}