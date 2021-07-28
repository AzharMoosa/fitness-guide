package com.example.fitnessapp.auth;

import static com.example.fitnessapp.auth.Authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.auth_data.LoginData;

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

  public void loginUser(View v) {
    // Retrieve Input
    String userEmail = email.getText().toString();
    String userPassword = password.getText().toString();

    // Login User
    LoginData loginData = new LoginData(userEmail, userPassword);
    login(loginData, this);
  }
}