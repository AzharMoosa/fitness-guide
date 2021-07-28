package com.example.fitnessapp;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.auth_data.UserData;
import com.example.fitnessapp.auth.Login;
import com.example.fitnessapp.auth.SignUp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    validToken(this);
  }

  // Check If Token Is Valid
  private void validToken(Context context) {
    ApiUtilities.getApiInterface()
        .getUserData(getToken(context))
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() != null) {
                  startActivity(new Intent(MainActivity.this, Dashboard.class));
                } else {
                  setContentView(R.layout.activity_main);
                }
              }

              @Override
              public void onFailure(Call<UserData> call, Throwable t) {
                setContentView(R.layout.activity_main);
              }
            });
  }

  // Disable Back Button
  @Override
  public void onBackPressed() {}

  // Go To Login Screen
  public void loginScreen(View v) {
    startActivity(new Intent(MainActivity.this, Login.class));
  }

  // Go To Sign Up Screen
  public void signUpScreen(View v) {
    startActivity(new Intent(MainActivity.this, SignUp.class));
  }
}
