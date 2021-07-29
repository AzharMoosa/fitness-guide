package com.example.fitnessguide.settings;

import static com.example.fitnessguide.auth.Authentication.getToken;
import static com.example.fitnessguide.auth.Authentication.getUserEmail;
import static com.example.fitnessguide.auth.Authentication.getUserID;
import static com.example.fitnessguide.auth.Authentication.getUserName;
import static com.example.fitnessguide.auth.Authentication.saveEmail;
import static com.example.fitnessguide.auth.Authentication.saveName;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessguide.Dashboard;
import com.example.fitnessguide.R;
import com.example.fitnessguide.api.ApiUtilities;
import com.example.fitnessguide.api.auth_data.UpdateUserData;
import com.example.fitnessguide.api.auth_data.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSettings extends AppCompatActivity {

  private EditText nameInput;
  private EditText emailInput;
  private EditText password;
  private EditText confirmPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_settings);
    nameInput = findViewById(R.id.name_input);
    emailInput = findViewById(R.id.email_input);
    nameInput.setText(getUserName(this));
    emailInput.setText(getUserEmail(this));
  }

  // Update User Settings
  public void updateUser(View v) {
    password = findViewById(R.id.password_input);
    confirmPassword = findViewById(R.id.confirm_password_input);
    // Check If Passwords Match
    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
      UpdateUserData updatedUserData =
          new UpdateUserData(
              nameInput.getText().toString(),
              emailInput.getText().toString(),
              password.getText().toString());
      updateSettings(updatedUserData);
    }
  }

  // Update Settings
  private void updateSettings(UpdateUserData updatedUserData) {
    ApiUtilities.getApiInterface()
        .updateUser(getToken(this), updatedUserData, getUserID(this))
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() != null) {
                  String name = response.body().getName();
                  String email = response.body().getEmail();
                  nameInput.setText(name);
                  emailInput.setText(email);
                  password.setText("");
                  confirmPassword.setText("");
                  saveName(name, getApplicationContext());
                  saveEmail(email, getApplicationContext());
                }
              }

              @Override
              public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(), "Cannot Update User Settings", Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }

  public void backBtn(View v) {
    startActivity(new Intent(this, Dashboard.class));
  }
}
