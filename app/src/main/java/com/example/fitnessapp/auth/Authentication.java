package com.example.fitnessapp.auth;

import static com.example.fitnessapp.constants.Constants.EMAIL;
import static com.example.fitnessapp.constants.Constants.ID;
import static com.example.fitnessapp.constants.Constants.NAME;
import static com.example.fitnessapp.constants.Constants.TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.example.fitnessapp.Dashboard;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.auth_data.LoginData;
import com.example.fitnessapp.api.auth_data.RegisterData;
import com.example.fitnessapp.api.auth_data.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authentication {

  // Login User
  public static void login(LoginData data, Context context) {
    ApiUtilities.getApiInterface()
        .loginUser(data)
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() != null) {
                  storeDetails(response.body(), context);
                  context.startActivity(new Intent(context, Dashboard.class));
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

  // Registers User
  public static void register(RegisterData data, Context context) {
    ApiUtilities.getApiInterface()
        .registerUser(data)
        .enqueue(
            new Callback<UserData>() {
              @Override
              public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() != null) {
                  storeDetails(response.body(), context);
                  context.startActivity(new Intent(context, Dashboard.class));
                }
              }

              @Override
              public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(
                        context.getApplicationContext(),
                        "Error: Registration Failed",
                        Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }

  // Save User Details
  private static void storeDetails(UserData userData, Context context) {
    saveToken(userData.getToken(), context);
    saveID(userData.getId(), context);
    saveName(userData.getName(), context);
    saveEmail(userData.getEmail(), context);
  }

  // Gets Current Token
  public static String getToken(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString(TOKEN, "");
  }

  // Gets Current Users ID
  public static String getUserID(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString(ID, "");
  }

  // Gets Current Users Name
  public static String getUserName(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString(NAME, "");
  }

  // Gets Current Users Email
  public static String getUserEmail(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getString(EMAIL, "");
  }

  // Saves Current Users ID
  public static void saveID(String id, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(ID, id);
    editor.apply();
  }

  // Saves Current Users Name
  public static void saveName(String name, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(NAME, name);
    editor.apply();
  }

  // Saves Current Users Email
  public static void saveEmail(String email, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(EMAIL, email);
    editor.apply();
  }

  // Saves Current Token
  public static void saveToken(String token, Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(TOKEN, token);
    editor.apply();
  }

  // Sign Out
  public static void signOut(Context context) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.clear();
    editor.commit();
  }
}
