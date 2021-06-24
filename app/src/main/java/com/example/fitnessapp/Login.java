package com.example.fitnessapp;

import static com.example.fitnessapp.auth.Authentication.login;
import static com.example.fitnessapp.constants.Constants.AUTH;
import static com.example.fitnessapp.constants.Constants.EMAIL;
import static com.example.fitnessapp.constants.Constants.PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

  private EditText email;
  private EditText password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    initialise();
  }

  private void initialise() {
    email = findViewById(R.id.email_login);
    password = findViewById(R.id.password_login);
  }

  public void signUpScreen(View v) {
    startActivity(new Intent(Login.this, SignUp.class));
  }

  public void loginUser(View v) throws JSONException {
    // Retrieve Input
    String userEmail = email.getText().toString();
    String userPassword = password.getText().toString();

    // Login User
    JSONObject data = new JSONObject();
    data.put(EMAIL, userEmail);
    data.put(PASSWORD, userPassword);
    login(AUTH, data, this);
  }
}