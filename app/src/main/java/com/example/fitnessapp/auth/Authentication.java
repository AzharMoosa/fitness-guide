package com.example.fitnessapp.auth;

import static com.example.fitnessapp.constants.Constants.API_URL;
import static com.example.fitnessapp.constants.Constants.TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapp.Dashboard;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Authentication {

  public static void login(String URL, JSONObject data, Context context) {
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + URL,
        data, response -> {
      try {
        String token = response.getString(TOKEN);
        context.startActivity(new Intent(context, Dashboard.class));
        saveToken(token, context);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }, error -> Toast
        .makeText(context.getApplicationContext(), "Error: Email Or Password Not Valid",
            Toast.LENGTH_SHORT)
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

  public static void register(String URL, JSONObject data, Context context) {
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + URL,
        data, response -> {
      try {
        String token = response.getString(TOKEN);
        context.startActivity(new Intent(context, Dashboard.class));
        saveToken(token, context);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }, error -> Toast
        .makeText(context.getApplicationContext(), "Error: Registration Failed" + error.toString(),
            Toast.LENGTH_SHORT).show()) {
      public Map<String, String> getParams() throws AuthFailureError {
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

  public static String getToken(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString("token", "");
  }

  private static void saveToken(String token, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(TOKEN, token);
    editor.apply();
  }



  public static void clearToken(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    editor.commit();
  }
}
