package com.example.fitnessapp.api;

import com.example.fitnessapp.routines.Session;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
  static final String BASE_URL = "https://fitness-application-api.herokuapp.com/api/";

  @GET("auth")
  Call<UserData> getUserData(@Header("x-auth-token") String auth);

  @GET("routines")
  Call<List<RoutinesData>> getRoutinesData(@Header("x-auth-token") String auth);

  @DELETE("routines/{id}")
  Call<ResponseBody> deleteRoutine(@Header("x-auth-token") String auth, @Path("id") String id);

  @GET("sessions/{id}")
  Call<Session> getSessionDataById(@Header("x-auth-token") String auth, @Path("id") String id);

  @POST("exercises")
  Call<ExerciseData> createExercise(@Header("x-auth-token") String auth, @Body ExerciseData exerciseData);

  @POST("sessions")
  Call<SessionData> createSession(@Header("x-auth-token") String auth, @Body SessionData sessionData);

  @POST("routines")
  Call<RoutinesData> createRoutine(@Header("x-auth-token") String auth, @Body RoutinesData routineData);
}
