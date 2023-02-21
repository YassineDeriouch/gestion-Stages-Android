package com.example.myapplication.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Instance {

    public Retrofit retrofit;
    public Retrofit_Instance(){
        initializeRetrofit();
    }
    public void initializeRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
    retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.57.13:9090")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
