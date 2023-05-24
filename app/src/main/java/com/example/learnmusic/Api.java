package com.example.learnmusic;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("upload_image.php")
    Call<ResponsePOJO> uploadImage(

            @Field("EN_IMAGE") String encodedImage,
            @Field("EMAIL") String email,
            @Field("PRODUCT_NAME") String product_name,
            @Field("PRODUCT_PRICE") String product_price,
            @Field("PRODUCT_CATEGORY") String product_category
    );
}
