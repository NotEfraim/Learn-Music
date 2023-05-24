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

import org.json.JSONArray;
import org.json.JSONObject;

public class Seller_pending_transaction extends AppCompatActivity {

    TextView product_name, product_price, product_category;
    TextView user_name, user_number, user_address, user_email;

    Button complete, cancel;
    ImageView product_image;
    String prod_id;

    String name, number, address, email, image_url;
    String pName, pPrice, pCategory, seller_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_pending_transaction);

        Intent intent = getIntent();

        product_image = findViewById(R.id.product_image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_category = findViewById(R.id.product_category);

        user_name = findViewById(R.id.user_name);
        user_number = findViewById(R.id.user_number);
        user_address = findViewById(R.id.user_address);
        user_email = findViewById(R.id.user_email);

        complete = findViewById(R.id.complete_button);
        cancel = findViewById(R.id.cancel_button);

        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        address = intent.getStringExtra("address");
        email = intent.getStringExtra("email");
        image_url = intent.getStringExtra("image_url");
        prod_id = intent.getStringExtra("prod_id");

        pName = intent.getStringExtra("prod_name");
        pPrice = intent.getStringExtra("prod_price");
        pCategory = intent.getStringExtra("category");
        seller_email = intent.getStringExtra("seller_email");


        product_name.setText(pName);
        product_price.setText(pPrice);
        product_category.setText(pCategory);

        user_name.setText(name);
        user_number.setText(number);
        user_address.setText(address);
        user_email.setText(email);
        Glide.with(Seller_pending_transaction.this).load(image_url).into(product_image);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelTransaction();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
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
                data[0] = prod_id;

                PutData putData = new PutData(BASE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        Toast.makeText(getApplicationContext(), prod_id, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SellerProfile.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        });
    }

    public void Confirm(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[9];
                field[0] = "seller_email";
                field[1] = "buyer_email";
                field[2] = "product_name";
                field[3] = "product_price";
                field[4] = "product_category";
                field[5] = "name";
                field[6] = "number";
                field[7] = "address";
                field[8] = "image_url";

                String[] data = new String[9];
                data[0] = seller_email;
                data[1] = email;
                data[2] = pName;
                data[3] = pPrice;
                data[4] = pCategory;
                data[5] = name;
                data[6] = number;
                data[7] = address;
                data[8] = image_url;

                PutData putData = new PutData("http://learn-music.click2drinkph.store/php/insert_transaction_history.php",
                        "POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){
                        Toast.makeText(getApplicationContext(),"Succes",Toast.LENGTH_SHORT).show();
                        CancelTransaction();
                    }

                }
            }
        });




    }


}