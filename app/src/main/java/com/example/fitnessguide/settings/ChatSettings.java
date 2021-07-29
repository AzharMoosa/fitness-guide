package com.example.fitnessguide.settings;

import static com.example.fitnessguide.auth.Authentication.getToken;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessguide.Dashboard;
import com.example.fitnessguide.R;
import com.example.fitnessguide.api.ApiUtilities;
import com.example.fitnessguide.api.settings_data.ChatInfo;
import com.example.fitnessguide.api.settings_data.SettingsData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatSettings extends AppCompatActivity {

  private ChatInfo info;
  private String settingsID;
  private EditText customName;

  @SuppressLint("UseSwitchCompatOrMaterialCode")
  private Switch displayName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_settings);
    getChatSettings();
  }

  // Gets Chat Settings
  private void getChatSettings() {
    ApiUtilities.getApiInterface()
        .getSettings(getToken(this))
        .enqueue(
            new Callback<SettingsData>() {
              @Override
              public void onResponse(Call<SettingsData> call, Response<SettingsData> response) {
                if (response.body() != null) {
                  settingsID = response.body().getId();
                  info = response.body().getChatSettings();
                  setInputs();
                }
              }

              @Override
              public void onFailure(Call<SettingsData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Cannot Get Chat Settings", Toast.LENGTH_SHORT).show();
              }
            });
  }

  // Sets Inputs
  private void setInputs() {
    customName = findViewById(R.id.custom_name);
    displayName = findViewById(R.id.display_in_chat);
    customName.setText(info.getCustomName());
    displayName.setChecked(info.getIsVisible());
  }

  // Reset Settings To Default
  public void resetToDefault(View v) {
    ChatInfo updatedInfo = new ChatInfo();
    updatedInfo.setCustomName(null);
    updatedInfo.setIsVisible(true);
    // Updates Chat Settings
    updateSettings(updatedInfo);
  }

  // Updates Chat Settings
  public void updateChatSettings(View v) {
    ChatInfo updatedInfo = new ChatInfo();
    updatedInfo.setCustomName(customName.getText().toString());
    updatedInfo.setIsVisible(displayName.isChecked());
    // Updates Chat Settings
    updateSettings(updatedInfo);
  }

  // Updates Settings
  private void updateSettings(ChatInfo updatedInfo) {
    ApiUtilities.getApiInterface()
        .updateChatSettings(getToken(this), updatedInfo, settingsID)
        .enqueue(
            new Callback<SettingsData>() {
              @Override
              public void onResponse(Call<SettingsData> call, Response<SettingsData> response) {
                if (response.body() != null) {
                  info = response.body().getChatSettings();
                  customName.setText(info.getCustomName());
                  displayName.setChecked(info.getIsVisible());
                }
              }

              @Override
              public void onFailure(Call<SettingsData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Cannot Update Chat Settings", Toast.LENGTH_SHORT).show();
              }
            });
  }

  public void backBtn(View v) {
    startActivity(new Intent(this, Dashboard.class));
  }
}
