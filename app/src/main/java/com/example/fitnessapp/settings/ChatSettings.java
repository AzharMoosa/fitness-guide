package com.example.fitnessapp.settings;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.Dashboard;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.ChatInfo;
import com.example.fitnessapp.api.SettingsData;
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
                Log.e("Error", t.getMessage());
              }
            });
  }

  public void backBtn(View v) {
    startActivity(new Intent(this, Dashboard.class));
  }

  private void setInputs() {
    customName = findViewById(R.id.custom_name);
    displayName = findViewById(R.id.display_in_chat);

    customName.setText(info.getCustomName());
    displayName.setChecked(info.getIsVisible());
  }

  public void updateChatSettings(View v) {
    ChatInfo updatedInfo = new ChatInfo();
    updatedInfo.setCustomName(customName.getText().toString());
    updatedInfo.setIsVisible(displayName.isChecked());

    ApiUtilities.getApiInterface()
        .updateChatSettings(getToken(this), updatedInfo, settingsID).enqueue(
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

          }
        });
  }

}