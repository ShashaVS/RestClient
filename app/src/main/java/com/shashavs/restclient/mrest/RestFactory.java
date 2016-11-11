package com.shashavs.restclient.mrest;


import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestFactory {

    private static final String BaseURL = "http://10.0.2.2:8080";

//    private static final int CONNECT_TIMEOUT = 15;
//    private static final int WRITE_TIMEOUT = 60;
//    private static final int TIMEOUT = 60;
//    private static final OkHttpClient CLIENT = new OkHttpClient();

    @NonNull
    public static RestService getRestService(boolean b) {
        if(b) {
            return getRetrofitJson().create(RestService.class);
        } else {
            return getRetrofitString().create(RestService.class);
        }
    }

    @NonNull
    private static Retrofit getRetrofitJson() {
        return new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())   //get GSON
                .build();
    }

    @NonNull
    private static Retrofit getRetrofitString() {
        return new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(ScalarsConverterFactory.create())  // get String
                .build();
    }
}
