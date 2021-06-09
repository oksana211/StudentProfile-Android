package com.example.studentprofile.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRetrofit {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("signin")
    Call<JsonObject> signin(@Body JsonObject locationPost);

    @POST("signup")
    Call<JsonObject> signup(@Body JsonObject locationPost);

    @GET("users")
    Call<JsonArray> getUsers(@Header("Authorization") String token);

    @GET("users/{id}")
    Call<JsonObject> getUser(@Header("Authorization") String token,
                            @Path("id") Long userId);

    @GET("statistic")
    Call<JsonObject> getStatistic(@Header("Authorization") String token);
}
