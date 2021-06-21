package com.example.fitnessapp;

import static com.example.fitnessapp.constants.Constants.API_URL;
import static com.example.fitnessapp.constants.Constants.AUTH;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

  private boolean isLogged = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      validToken();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void validToken() throws JSONException {
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + AUTH,
        null, response -> {
      startActivity(new Intent(MainActivity.this, Dashboard.class));
      isLogged = true;
    }, error -> setContentView(R.layout.activity_main)) {
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-auth-token", getToken());
        return headers;
      }
    };
    int socketTimeout = 10000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    objectRequest.setRetryPolicy(policy);
    requestQueue.add(objectRequest);
  }

  private String getToken() {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    return preferences.getString("token", "");
  }

  public void loginScreen(View v) {
    startActivity(new Intent(MainActivity.this, Login.class));
  }

  public void signUpScreen(View v) {
    startActivity(new Intent(MainActivity.this, SignUp.class));
  }
}