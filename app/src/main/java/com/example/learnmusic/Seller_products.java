package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Seller_products extends AppCompatActivity {

    private RecyclerView seller_productRecycle;
    private List<SellerProduct> lstproduct;
    private SellerProductAdapter sellerProductAdapter;

    SharedPreferences sharedPreferences;
    String SP_ID, sharedEmail;

    Button edit, delete;
    ImageView back;
    TextView empty_alert;

    // RECYCLER
    RecyclerView recyclerView;
    RecyclerView.Adapter SellerAdapter;
    List<MarketProductList> marketProductLists;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_products);

        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id", "");
        sharedEmail = sharedPreferences.getString("email", "");

        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.back);
        empty_alert = findViewById(R.id.empty_alert);

        //
        recyclerView = findViewById(R.id.seller_productRecycle);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SellerProfile.class);
                startActivity(intent);
                finish();
            }
        });


        getSellerProducts();
    }

    public void getSellerProducts(){

        marketProductLists = new ArrayList<>();
        layoutManager =new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] ="email";

                String[] data = new String[1];
                data[0] =sharedEmail;

                String BASE_URL ="http://learn-music.click2drinkph.store/php/seller_products.php";

                PutData putData = new PutData(BASE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);

                                String id = object.getString("id");
                                String seller_email = object.getString("seller_email");
                                String name = object.getString("product_name");
                                String price = object.getString("product_price");
                                String category = object.getString("product_category");
                                String image_url = object.getString("image_url");
                                String url = "http://learn-music.click2drinkph.store/images/"+image_url;

                                MarketProductList datax = new MarketProductList(id,seller_email,name,price,category,url);
                                marketProductLists.add(datax);
                            }
                        }
                        catch (Exception e){
                            //CATCH ERROR HERE
                            empty_alert.setVisibility(View.VISIBLE);
                        }

                        SellerAdapter = new SellerProductAdapter(getApplicationContext(),marketProductLists);
                        recyclerView.setAdapter(SellerAdapter);

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