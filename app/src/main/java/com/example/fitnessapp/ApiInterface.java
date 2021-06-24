package com.example.fitnessapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiInterface {
  static final String BASE_URL = "https://fitness-application-api.herokuapp.com/api/";

  @GET("auth")
  Call<UserData> getUserData(@Header("x-auth-token") String auth);

}
