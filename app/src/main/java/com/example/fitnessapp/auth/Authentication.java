package com.example.fitnessapp.auth;

import static com.example.fitnessapp.constants.Constants.ID;
import static com.example.fitnessapp.constants.Constants.TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.example.fitnessapp.Dashboard;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.LoginData;
import com.example.fitnessapp.api.RegisterData;
import com.example.fitnessapp.api.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authentication {

  public static void login(LoginData data, Context context) {
    ApiUtilities.getApiInterface()
        .loginUser(data)
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() != null) {
                  String token = response.body().getToken();
                  String id = response.body().getId();
                  String name = response.body().getName();
                  String email = response.body().getEmail();
                  context.startActivity(new Intent(context, Dashboard.class));
                  saveToken(token, context);
                  saveID(id, context);
                  saveName(name, context);
                  saveEmail(email, context);
                }
              }

              @Override
              public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(
                        context.getApplicationContext(),
                        "Error: Email Or Password Not Valid",
                        Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }

  public static void register(RegisterData data, Context context) {
    ApiUtilities.getApiInterface()
        .registerUser(data)
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                String token = response.body().getToken();
                String id = response.body().getId();
                String name = response.body().getName();
                String email = response.body().getEmail();
                context.startActivity(new Intent(context, Dashboard.class));
                saveToken(token, context);
                saveID(id, context);
                saveName(name, context);
                saveEmail(email, context);
              }

              @Override
              public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(
                        context.getApplicationContext(),
                        "Error: Registration Failed " + t.getMessage(),
                        Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }

  public static String getToken(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString("token", "");
  }

  public static String getUserID(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString("id", "");
  }

  public static void saveID(String id, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(ID, id);
    editor.apply();
  }

  public static String getUserName(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString("name", "");
  }

  public static void saveName(String name, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString("name", name);
    editor.apply();
  }

  public static String getUserEmail(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString("email", "");
  }

  public static void saveEmail(String email, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString("email", email);
    editor.apply();
  }

  public static void saveToken(String token, Context context) {
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
