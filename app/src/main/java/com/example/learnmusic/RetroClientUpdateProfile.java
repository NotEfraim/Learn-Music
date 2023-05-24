package com.example.learnmusic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClientUpdateProfile {

    private static final String BASE_URL ="http://learn-music.click2drinkph.store/php/";
    private static RetroClientUpdateProfile myClient;
    private Retrofit retrofit;

    private RetroClientUpdateProfile(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetroClientUpdateProfile getInstance(){

        if(myClient == null){
            myClient = new RetroClientUpdateProfile();
        }
        return myClient;
    }

    public Api_update_product getApi(){
        return retrofit.create(Api_update_product.class);
    }
}
