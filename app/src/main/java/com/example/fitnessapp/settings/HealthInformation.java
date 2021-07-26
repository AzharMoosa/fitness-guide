package com.example.fitnessapp.settings;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.Dashboard;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.HealthInfo;
import com.example.fitnessapp.api.SettingsData;
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
                Log.e("Error", t.getMessage());
              }
            });
  }

  public void backBtn(View v) {
    startActivity(new Intent(this, Dashboard.class));
  }

  private void setInputs() {
    dobInput = findViewById(R.id.dob);
    genderInput = findViewById(R.id.gender);
    heightInput = findViewById(R.id.height);
    weightInput = findViewById(R.id.weight);

    dobInput.setText(info.getDateOfBirth());
    genderInput.setText(info.getGender());
    if (info.getHeight() != 0) {
      heightInput.setText(info.getHeight().toString());
    }
    if (info.getWeight() != 0) {
      weightInput.setText(info.getWeight().toString());
    }
  }

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

    ApiUtilities.getApiInterface()
        .updateHealthSettings(getToken(this), updatedInfo, settingsID)
        .enqueue(
            new Callback<SettingsData>() {
              @Override
              public void onResponse(Call<SettingsData> call, Response<SettingsData> response) {
                if (response.body() != null) {
                  info = response.body().getHealthInformation();
                  dobInput.setText(info.getDateOfBirth());
                  genderInput.setText(info.getGender());
                  if (info.getHeight() != 0) {
                    heightInput.setText(info.getHeight().toString());
                  }
                  if (info.getWeight() != 0) {
                    weightInput.setText(info.getWeight().toString());
                  }
                }
              }

              @Override
              public void onFailure(Call<SettingsData> call, Throwable t) {}
            });
  }
}
