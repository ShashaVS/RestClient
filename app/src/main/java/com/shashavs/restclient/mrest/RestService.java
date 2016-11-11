package com.shashavs.restclient.mrest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestService {

    @GET("home/get/str")
    Call<String> methodGet(@Query("param") String name);

    @GET("home/get/json")
    Call<mResponseClient> methodGetJSON(@Query("name") String name, @Query("age") int age);

    @FormUrlEncoded
    @POST("home/post")
    Call<String> methodPost(@Field("login") String login, @Field("password") String password);
}
