package com.example.fitnessapp;

import static com.example.fitnessapp.auth.Authentication.getToken;
import static com.example.fitnessapp.constants.Constants.API_URL;
import static com.example.fitnessapp.constants.Constants.AUTH;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapp.auth.Login;
import com.example.fitnessapp.auth.SignUp;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      validToken(this);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void validToken(Context context) throws JSONException {
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + AUTH,
        null, response -> {
      startActivity(new Intent(MainActivity.this, Dashboard.class));
    }, error -> setContentView(R.layout.activity_main)) {
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-auth-token", getToken(context));
        return headers;
      }
    };
    int socketTimeout = 10000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    objectRequest.setRetryPolicy(policy);
    requestQueue.add(objectRequest);
  }

  public void loginScreen(View v) {
    startActivity(new Intent(MainActivity.this, Login.class));
  }

  public void signUpScreen(View v) {
    startActivity(new Intent(MainActivity.this, SignUp.class));
  }
}