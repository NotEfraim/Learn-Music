package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

public class SellerProfile extends AppCompatActivity {

    ConstraintLayout upload_product;
    CardView view_products;
    CardView order_list;
    CardView transaction;

    ImageView back;
    String email;
    SharedPreferences sharedPreferences;
    TextView seller_name, seller_email;
    ImageView seller_pic;
    Button edit_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        upload_product = findViewById(R.id.upload_product);
        back = findViewById(R.id.Back);
        seller_name = findViewById(R.id.seller_name);
        seller_email = findViewById(R.id.email);
        seller_pic = findViewById(R.id.seller_pic);
        edit_profile = findViewById(R.id.edit_profile);
        view_products = findViewById(R.id.view_products);
        order_list = findViewById(R.id.order_list);
        transaction = findViewById(R.id.pending_transaction);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        upload_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddProduct.class);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserProfileEdit.class);
                startActivity(intent);
                finish();
            }
        });

        view_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Seller_products.class);
                startActivity(intent);
                finish();
            }
        });

        order_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Seller_view_orders.class);
                startActivity(intent);
                finish();
            }
        });

        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Transaction_history.class);
                startActivity(intent);
                finish();
            }
        });



        ViewProfile();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        finish();
    }

    private void ViewProfile() {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = email;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_profile.php","POST",field,data);

                if(putData.startPut()){
                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);
                                String email = object.getString("email");
                                String first_name = object.getString("firstname");
                                String last_name = object.getString("lastname");
                                String img = object.getString("image_url");

                                seller_email.setText(email);
                                seller_name.setText(first_name+" "+last_name);

                                if(!img.equals("")){
                                    String img_url = "http://learn-music.click2drinkph.store/images/"+img;
                                    Glide.with(getApplicationContext()).load(img_url).into(seller_pic);
                                }
                                else {
                                    seller_pic.setImageResource(R.drawable.pic);
                                }

                            }

                        } catch (Exception e) {
                            //remove this in production
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error in Internet Connection",Toast.LENGTH_LONG).show();
                }


            }

        });

    }
}