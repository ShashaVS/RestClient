package com.shashavs.restclient.mrest;

import com.google.gson.annotations.SerializedName;

public class mResponseClient {

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
