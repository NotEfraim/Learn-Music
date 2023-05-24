package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class View_user_pending_transaction extends AppCompatActivity {

    TextView product_name;
    TextView product_price;
    TextView product_category;

    TextView seller_name;
    TextView seller_number;
    TextView seller_address;
    TextView seller_email;

    String columnID;

    Button cancel_button;
    ImageView product_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_pending_transaction);

        Intent intent = getIntent();

        String pName = intent.getStringExtra("prod_name");
        String pPrice = intent.getStringExtra("prod_price");
        String pCategory = intent.getStringExtra("prod_category");
        String image_url = intent.getStringExtra("image_url");

        String sName = intent.getStringExtra("sName");
        String sNumber = intent.getStringExtra("sNumber");
        String sEmail = intent.getStringExtra("sEmail");
        String sAddress = intent.getStringExtra("sAddress");

        columnID = intent.getStringExtra("id");




        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_category = findViewById(R.id.product_category);
        product_picture = findViewById(R.id.product_image);

        seller_name = findViewById(R.id.seller_name);
        seller_number = findViewById(R.id.seller_number);
        seller_address = findViewById(R.id.seller_address);
        seller_email = findViewById(R.id.seller_email);

        product_name.setText(pName);
        product_price.setText(pPrice);
        product_category.setText(pCategory);
        Glide.with(View_user_pending_transaction.this).load(image_url).into(product_picture);

        seller_name.setText(sName);;
        seller_number.setText(sNumber);
        seller_address.setText(sAddress);
        seller_email.setText(sEmail);

        cancel_button = findViewById(R.id.cancel_button);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelTransaction();
            }
        });


    }

    public void CancelTransaction(){

        String BASE_URL = "http://learn-music.click2drinkph.store/php/delete_pending_transaction.php";

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = columnID;

                PutData putData = new PutData(BASE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        Intent intent = new Intent(getApplicationContext(), User_pending_transaction.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        });
    }
}