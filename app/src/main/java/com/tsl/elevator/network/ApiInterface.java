package com.tsl.elevator.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tsl.elevator.models.resources.ResourcesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("/api/v2/oauth2/token")
    Call<JsonObject> getNewToken(@Field("grant_type") String grantType,
                                 @Field("scope") String scope);


    @GET("/api/v2/oauth2/userinfo")
    Call<JsonObject> getUserInfo(@Header("Authorization") String authToken);

    @GET("/api/v2/application/self/resources")
    Call<ResourcesResponse> getKoneRes(@Header("Content-Type") String contentType, @Header("Authorization") String authToken);


}
