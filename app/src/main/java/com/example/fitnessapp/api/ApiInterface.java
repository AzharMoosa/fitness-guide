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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
  static final String BASE_URL = "https://fitness-application-api.herokuapp.com/api/";

  @GET("auth")
  Call<UserData> getUserData(@Header("x-auth-token") String auth);

  @GET("routines")
  Call<List<RoutinesData>> getRoutinesData(@Header("x-auth-token") String auth);

  @GET("routines/active")
  Call<List<RoutinesData>> getActiveRoutine(@Header("x-auth-token") String auth);

  @DELETE("routines/{id}")
  Call<ResponseBody> deleteRoutine(@Header("x-auth-token") String auth, @Path("id") String id);

  @PUT("routines/{id}")
  Call<RoutinesData> updateRoutine(
      @Header("x-auth-token") String auth, @Body RoutinesData routineData, @Path("id") String id);

  @GET("sessions/{id}")
  Call<Session> getSessionDataById(@Header("x-auth-token") String auth, @Path("id") String id);

  @PUT("users/{id}")
  Call<UserData> updateUser(@Header("x-auth-token") String auth, @Body UpdateUserData userData, @Path("id") String id);

  @POST("routines")
  Call<RoutinesData> createRoutine(
      @Header("x-auth-token") String auth, @Body RoutinesData routineData);

  @GET("exercises/type/{type}")
  Call<List<ExerciseData>> getExercisesByType(@Path("type") String type);
}
