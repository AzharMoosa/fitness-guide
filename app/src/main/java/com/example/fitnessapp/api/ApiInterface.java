package com.example.fitnessapp.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
  static final String BASE_URL = "https://fitness-application-api.herokuapp.com/api/";

  @GET("auth")
  Call<UserData> getUserData(@Header("x-auth-token") String auth);

  @GET("routines")
  Call<List<RoutinesData>> getRoutinesData(@Header("x-auth-token") String auth);

  @POST("exercises")
  Call<ExerciseData> createExercise(@Header("x-auth-token") String auth, @Body ExerciseData exerciseData);

  @POST("sessions")
  Call<SessionData> createSession(@Header("x-auth-token") String auth, @Body SessionData sessionData);

  @POST("routines")
  Call<RoutinesData> createRoutine(@Header("x-auth-token") String auth, @Body RoutinesData routineData);
}
