package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.fitnessapp.constants.Constants.*;

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

    // Create User
    JSONObject data = new JSONObject();
    data.put(EMAIL, userEmail);
    data.put(PASSWORD, userPassword);
    login(AUTH, data);
  }

  private void login(String URL, JSONObject data) {
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + URL,
        data, response -> {
      try {
        String token = response.getString(TOKEN);
        startActivity(new Intent(Login.this, Dashboard.class));
        saveToken(token);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }, error -> Toast
        .makeText(getApplicationContext(), "Error: Email Or Password Not Valid", Toast.LENGTH_SHORT)
        .show()) {
      public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        return params;
      }
    };
    int socketTimeout = 10000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    objectRequest.setRetryPolicy(policy);
    requestQueue.add(objectRequest);
  }

  private void saveToken(String token) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(TOKEN, token);
    editor.apply();
  }
}