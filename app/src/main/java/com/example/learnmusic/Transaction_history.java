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

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Transaction_history extends AppCompatActivity {

    private RecyclerView transactionRecycle;
    private List<Transaction_history_List> lstHistory;
    private Transaction_Adaper transaction_adaper;
    private RecyclerView.LayoutManager layoutManager;

    ImageView back;

    String SP_ID;
    String sharedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id","");
        sharedEmail = sharedPreferences.getString("email", "");

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transaction_history.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        transactionRecycle = findViewById(R.id.transaction_recycle);

        GetTransactionHistory();
    }

    public void GetTransactionHistory(){
        lstHistory = new ArrayList<>();
        layoutManager = new LinearLayoutManager(Transaction_history.this, RecyclerView.VERTICAL, false);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "seller_email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/fetch_transaction_history.php", "POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);
                            for(int i=0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String seller_email = object.getString("seller_email");
                                String buyer_email = object.getString("buyer_email");
                                String product_name = object.getString("product_name");
                                String product_price = object.getString("product_price");
                                String product_category = object.getString("product_category");
                                String buyer_name = object.getString("name");
                                String number = object.getString("number");
                                String address = object.getString("address");
                                String image_url = object.getString("image_url");

                                Transaction_history_List DataX = new Transaction_history_List(buyer_email, buyer_name, product_name, product_price, product_category,
                                        image_url);

                                lstHistory.add(DataX);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                        transaction_adaper = new Transaction_Adaper(Transaction_history.this, lstHistory);
                        transactionRecycle.setAdapter(transaction_adaper);
                        transactionRecycle.setLayoutManager(layoutManager);

                    }


                }







            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Transaction_history.this, Home.class);
        startActivity(intent);
        finish();
    }
}