package com.example.learnmusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_seller_product extends AppCompatActivity {

    ImageView product_img;
    EditText instrument;
    EditText price;
    EditText category;
    Button update;
    Button file_button;
    String sharedEmail;
    String product_id;

    //UPLOADER
    private Bitmap bitmap;
    private int IMG_REQUEST = 21;
    String instrument_txt, category_txt, price_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_seller_product);

        Intent intent = getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        sharedEmail = sharedPreferences.getString("email", "");

        product_img = findViewById(R.id.product_img);
        instrument = findViewById(R.id.edit_instrument);
        price = findViewById(R.id.edit_price);
        category = findViewById(R.id.edit_description);
        update = findViewById(R.id.update_product);
        file_button = findViewById(R.id.file_button);

        String image_url = intent.getStringExtra("image_url");

        product_id = intent.getStringExtra("id");
        Glide.with(this).load(image_url).into(product_img);
        instrument.setText(intent.getStringExtra("product_name"));
        price.setText(intent.getStringExtra("product_price"));
        category.setText(intent.getStringExtra("product_category"));

        file_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                instrument_txt = instrument.getText().toString();
                price_txt = price.getText().toString();
                category_txt = category.getText().toString();

                UploadImage();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data !=null){

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                product_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public void UploadProduct(){
//
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                String[] field = new String[4];
//                field[0] = "seller_user_id";
//                field[1] = "product_quantity";
//                field[2] = "product_name";
//                field[3] = "product_price";
//                field[4] = "product_description";
//
//                String[] data = new String[4];
//                data[0] = sharedID;
//                data[1] = product_quantity;
//                data[2] = product_name;
//                data[3] = product_price;
//                data[4] = product_description;
//
//                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/Seller_UploadProduct.php", "POST", field,data);
//
//                if(putData.startPut()){
//                    if(putData.onComplete()){
//
//                        String result = putData.getResult();
//
//                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), Home.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Error in Internet Connection",Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//        });
//    }

    public void UploadImage(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);

        byte[] image_byte = byteArrayOutputStream.toByteArray();
        String encoded_image = Base64.encodeToString(image_byte, Base64.DEFAULT);
//        Toast.makeText(getApplicationContext(),encoded_image,Toast.LENGTH_SHORT).show();
        Call<ResponsePOJO> call = RetroClientUpdateProfile.getInstance().getApi().uploadImage(
                product_id,encoded_image,sharedEmail, instrument_txt,price_txt,category_txt);
        call.enqueue(new Callback<ResponsePOJO>() {
            @Override
            public void onResponse(Call<ResponsePOJO> call, Response<ResponsePOJO> response) {
//                Toast.makeText(getApplicationContext(), response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                if(response.body().isStatus()){
                    Intent intent = new Intent(getApplicationContext(),SellerProfile.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePOJO> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Network Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }
}