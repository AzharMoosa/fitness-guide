package com.example.fitnessapp.api;

import static com.example.fitnessapp.constants.Constants.API_URL;

import com.example.fitnessapp.api.auth_data.LoginData;
import com.example.fitnessapp.api.auth_data.RegisterData;
import com.example.fitnessapp.api.auth_data.UpdateUserData;
import com.example.fitnessapp.api.auth_data.UserData;
import com.example.fitnessapp.api.exercise_data.ExerciseData;
import com.example.fitnessapp.api.routines_data.RoutinesData;
import com.example.fitnessapp.api.settings_data.ChatInfo;
import com.example.fitnessapp.api.settings_data.HealthInfo;
import com.example.fitnessapp.api.settings_data.SettingsData;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
  // API URL
  String BASE_URL = API_URL;

  // Gets User Data
  @GET("auth")
  Call<UserData> getUserData(@Header("x-auth-token") String auth);

  // Login User
  @POST("auth")
  Call<UserData> loginUser(@Body LoginData loginData);

  // Register User
  @POST("users")
  Call<UserData> registerUser(@Body RegisterData registerData);

  // Updates User
  @PUT("users/{id}")
  Call<UserData> updateUser(
      @Header("x-auth-token") String auth, @Body UpdateUserData userData, @Path("id") String id);

  // Gets Routine Data
  @GET("routines")
  Call<List<RoutinesData>> getRoutinesData(@Header("x-auth-token") String auth);

  // Gets Active Routine
  @GET("routines/active")
  Call<RoutinesData> getActiveRoutine(@Header("x-auth-token") String auth);

  // Creates Routine
  @POST("routines")
  Call<RoutinesData> createRoutine(
      @Header("x-auth-token") String auth, @Body RoutinesData routineData);

  // Updates Routine
  @PUT("routines/{id}")
  Call<RoutinesData> updateRoutine(
      @Header("x-auth-token") String auth, @Body RoutinesData routineData, @Path("id") String id);

  // Deletes Routine
  @DELETE("routines/{id}")
  Call<ResponseBody> deleteRoutine(@Header("x-auth-token") String auth, @Path("id") String id);

  // Gets Exercises By Type
  @GET("exercises/type/{type}")
  Call<List<ExerciseData>> getExercisesByType(@Path("type") String type);

  // Gets User Settings
  @GET("settings")
  Call<SettingsData> getSettings(@Header("x-auth-token") String auth);

  // Updates Health Settings
  @PUT("settings/{id}")
  Call<SettingsData> updateHealthSettings(
      @Header("x-auth-token") String auth, @Body HealthInfo healthInfo, @Path("id") String id);

  // Updates Chat Settings
  @PUT("settings/{id}")
  Call<SettingsData> updateChatSettings(
      @Header("x-auth-token") String auth, @Body ChatInfo chatInfo, @Path("id") String id);
}
