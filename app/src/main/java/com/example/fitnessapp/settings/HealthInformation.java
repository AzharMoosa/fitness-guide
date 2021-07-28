package com.example.fitnessapp.settings;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.Dashboard;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.settings_data.HealthInfo;
import com.example.fitnessapp.api.settings_data.SettingsData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthInformation extends AppCompatActivity {

  private HealthInfo info;
  private String settingsID;
  private EditText dobInput;
  private EditText genderInput;
  private EditText heightInput;
  private EditText weightInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_health_information);
    dobInput = findViewById(R.id.dob);
    genderInput = findViewById(R.id.gender);
    heightInput = findViewById(R.id.height);
    weightInput = findViewById(R.id.weight);
    getHealthSettings();
  }

  // Gets Health Settings
  private void getHealthSettings() {
    ApiUtilities.getApiInterface()
        .getSettings(getToken(this))
        .enqueue(
            new Callback<SettingsData>() {
              @Override
              public void onResponse(Call<SettingsData> call, Response<SettingsData> response) {
                if (response.body() != null) {
                  settingsID = response.body().getId();
                  info = response.body().getHealthInformation();
                  setInputs();
                }
              }

              @Override
              public void onFailure(Call<SettingsData> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "Cannot Get Health Information",
                        Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }

  // Sets Inputs
  @SuppressLint("SetTextI18n")
  private void setInputs() {
    dobInput.setText(info.getDateOfBirth());
    genderInput.setText(info.getGender());
    if (info.getHeight() != 0) {
      heightInput.setText(info.getHeight().toString());
    }
    if (info.getWeight() != 0) {
      weightInput.setText(info.getWeight().toString());
    }
  }

  // Updates Health Settings
  public void updateInfo(View v) {
    HealthInfo updatedInfo = new HealthInfo();
    updatedInfo.setDateOfBirth(dobInput.getText().toString());
    updatedInfo.setGender(genderInput.getText().toString());
    if (!heightInput.getText().toString().equals("")) {
      updatedInfo.setHeight(Integer.parseInt(heightInput.getText().toString()));
    }
    if (!weightInput.getText().toString().equals("")) {
      updatedInfo.setWeight(Integer.parseInt(weightInput.getText().toString()));
    }
    updateSettings(updatedInfo);
  }

  // Updates Settings
  private void updateSettings(HealthInfo updatedInfo) {
    ApiUtilities.getApiInterface()
        .updateHealthSettings(getToken(this), updatedInfo, settingsID)
        .enqueue(
            new Callback<SettingsData>() {
              @SuppressLint("SetTextI18n")
              @Override
              public void onResponse(Call<SettingsData> call, Response<SettingsData> response) {
                if (response.body() != null) {
                  info = response.body().getHealthInformation();
                  setInputs();
                }
              }

              @Override
              public void onFailure(Call<SettingsData> call, Throwable t) {
                Toast.makeText(
                    getApplicationContext(),
                    "Cannot Update Health Information",
                    Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }


  public void backBtn(View v) {
    startActivity(new Intent(this, Dashboard.class));
  }
}
