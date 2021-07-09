package com.example.fitnessapp.auth;

import static com.example.fitnessapp.auth.Authentication.register;
import static com.example.fitnessapp.constants.Constants.EMAIL;
import static com.example.fitnessapp.constants.Constants.NAME;
import static com.example.fitnessapp.constants.Constants.PASSWORD;
import static com.example.fitnessapp.constants.Constants.USERS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.R;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

  private EditText name;
  private EditText email;
  private EditText password;
  private EditText confirmPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up2);
    initialise();
  }

  private void initialise() {
    name = findViewById(R.id.name_signup);
    email = findViewById(R.id.email_signup);
    password = findViewById(R.id.password_signup);
    confirmPassword = findViewById(R.id.confirm_password);
  }

  public void loginScreen(View v) {
    startActivity(new Intent(SignUp.this, Login.class));
  }

  public void registerUser(View v) throws JSONException {
    // Retrieve Input
    String userName = name.getText().toString();
    String userEmail = email.getText().toString();
    String userPassword = password.getText().toString();
    String userConfirmPassword = confirmPassword.getText().toString();

    if (!userPassword.equals(userConfirmPassword)) {
      Toast.makeText(this, "Password Does Not Match", Toast.LENGTH_SHORT);
    } else {
      // Sign Up
      JSONObject data = new JSONObject();
      data.put(NAME, userName);
      data.put(EMAIL, userEmail);
      data.put(PASSWORD, userPassword);
      register(USERS, data, this);
    }
  }
}
