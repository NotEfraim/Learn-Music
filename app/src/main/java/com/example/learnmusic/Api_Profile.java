package com.example.learnmusic;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api_Profile {

    @FormUrlEncoded
    @POST("upload_profile.php")
    Call<ResponsePOJO> uploadImage(

            @Field("EN_IMAGE") String encodedImage,
            @Field("EMAIL") String email

    );
}
