package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Seller_view_orders extends AppCompatActivity {

    private RecyclerView sellerRecycleView;
    private List<Seller_view_order_List> lstViewOrders;
    private Seller_view_order_Adapter seller_view_order_adapter;
    RecyclerView.LayoutManager layoutManager;
    ImageView back;

    //
    private static String PENDING_URL = "http://learn-music.click2drinkph.store/php/fetch_pending_transaction.php";
    SharedPreferences sharedPreferences;
    String sharedEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_view_orders);
        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        sharedEmail = sharedPreferences.getString("email", "");

        sellerRecycleView = findViewById(R.id.seller_recyclerView);
        back = findViewById(R.id.back);

        GetOrder();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SellerProfile.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void GetOrder(){

        lstViewOrders = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData(PENDING_URL,"POST",field,data);
                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);
                            for(int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");
                                String seller_email = object.getString("seller_email");
                                String user_id = object.getString("user_id");
                                String product_id = object.getString("product_id");
                                String product_name = object.getString("product_name");
                                String product_price = object.getString("product_price");
                                String product_category = object.getString("product_category");
                                String image_url = object.getString("image_url");

                                Seller_view_order_List datax = new Seller_view_order_List(id,user_id,seller_email,product_id,
                                        product_name,product_price,product_category,image_url);

                                lstViewOrders.add(datax);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        seller_view_order_adapter = new Seller_view_order_Adapter
                                (getApplicationContext(),lstViewOrders);
                        sellerRecycleView.setAdapter(seller_view_order_adapter);
                        sellerRecycleView.setLayoutManager(layoutManager);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),SellerProfile.class);
        startActivity(intent);
        finish();
    }
}