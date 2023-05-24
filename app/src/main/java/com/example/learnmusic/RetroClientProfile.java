package com.example.learnmusic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClientProfile {
    private static final String BASE_URL ="http://learn-music.click2drinkph.store/php/";
    private static RetroClientProfile myClient;
    private Retrofit retrofit;

    private RetroClientProfile(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetroClientProfile getInstance(){

        if(myClient == null){
            myClient = new RetroClientProfile();
        }
        return myClient;
    }

    public Api_Profile getApi(){
        return retrofit.create(Api_Profile.class);
    }
}
