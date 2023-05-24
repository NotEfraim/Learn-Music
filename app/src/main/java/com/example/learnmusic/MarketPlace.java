package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.List;

public class MarketPlace extends AppCompatActivity {

    private RecyclerView market_recycleview;
    private List<MarketProductList> lstMarketProductList;
    private MarketRecycleAdapter marketRecycleAdapter;

    SharedPreferences sharedPreferences;
    String SP_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);

        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id", "");

    }
}