package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Purchase extends AppCompatActivity {

    private RecyclerView purchase_recycle;
    private List<Purchase_list> lstpurchase;
    private RecyclerView.LayoutManager layoutManager;
    private Purchase_Adapter purchase_adapter;

    String SP_ID;
    String sharedEmail;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id","");
        sharedEmail = sharedPreferences.getString("email", "");

        purchase_recycle = findViewById(R.id.purchase_recycle);

        back = findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Purchase.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        FetchHistory();
    }

    public void FetchHistory(){
        lstpurchase = new ArrayList<>();
        layoutManager = new LinearLayoutManager(Purchase.this,RecyclerView.VERTICAL, false);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "buyer_email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData("http://learn-music.click2drinkph.store/php/fetch_user_transaction_history.php",
                        "POST", field, data);

                if (putData.startPut()){

                    if (putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);
                            for(int i=0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String buyer_email = object.getString("buyer_email");
                                String product_name = object.getString("product_name");
                                String product_price = object.getString("product_price");
                                String product_category = object.getString("product_category");
                                String name = object.getString("name");
                                String number = object.getString("number");
                                String address = object.getString("address");
                                String image_url = object.getString("image_url");


                                Purchase_list datax = new Purchase_list(buyer_email, product_name, product_price, product_category,
                                        name, number, address, image_url);
                                lstpurchase.add(datax);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        purchase_adapter = new Purchase_Adapter(Purchase.this, lstpurchase);
                        purchase_recycle.setAdapter(purchase_adapter);
                        purchase_recycle.setLayoutManager(layoutManager);
                    }


                }






            }
        });



    }




}