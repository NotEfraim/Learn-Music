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
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User_pending_transaction extends AppCompatActivity {

    private RecyclerView user_transaction_recycle;
    private List<User_pending_list> lstUser_pending;
    private RecyclerView.Adapter user_pending_adapter;
    private RecyclerView.LayoutManager pManager;

    SharedPreferences sharedPreferences;
    String logged_id;
    ImageView back;
    TextView empty_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pending_transaction);

        user_transaction_recycle = findViewById(R.id.user_transaction_recycle);
        back = findViewById(R.id.back_view);
        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        logged_id = sharedPreferences.getString("Logged_user_id","");

        empty_alert = findViewById(R.id.empty_alert);

        GetTransactionHistory();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void GetTransactionHistory(){

        lstUser_pending = new ArrayList<>();
        pManager = new LinearLayoutManager(User_pending_transaction.this,RecyclerView.VERTICAL,false);


        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            String PENDING_URL ="http://learn-music.click2drinkph.store/php/fetch_pending_order_user.php";

            String[] field = new String[1];
            field[0] = "id";

            String[] data = new String[1];
            data[0] = logged_id;

                PutData putData = new PutData(PENDING_URL,"POST",field, data);
                 if(putData.startPut()){
                     if(putData.onComplete()){
                         String result = putData.getResult();

                         try {
                             JSONArray array = new JSONArray(result);
                             for(int i = 0; i<array.length(); i++){

                                 JSONObject object = array.getJSONObject(i);
                                 String id = object.getString("id");
                                 String s_email = object.getString("seller_email");
                                 String user_id = object.getString("user_id");
                                 String prod_id = object.getString("product_id");
                                 String prod_name = object.getString("product_name");
                                 String prod_price = object.getString("product_price");
                                 String prod_category = object.getString("product_category");
                                 String image_url = object.getString("image_url");

                                 User_pending_list datax = new User_pending_list(id,s_email,user_id,prod_id,prod_name,prod_price,prod_category,image_url);
                                 lstUser_pending.add(datax);

                             }
                         } catch (JSONException e) {
                             empty_alert.setVisibility(View.VISIBLE);
                             e.printStackTrace();
                         }

                         user_pending_adapter = new User_pending_Adapter(User_pending_transaction.this,lstUser_pending);
                         user_transaction_recycle.setAdapter(user_pending_adapter);
                         user_transaction_recycle.setLayoutManager(pManager);
                     }
                 }


            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        finish();
    }
}