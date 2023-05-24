package com.example.learnmusic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MarketPlaceFragment extends Fragment {

    // for tablayoutview
    View view;
    // recycleview
    private RecyclerView market_recyclerView;
    private List<MarketProductList> lstMarketProductList;
    private MarketRecycleAdapter marketRecycleAdapter;

    public MarketPlaceFragment (){
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_market_place, container, false);

        market_recyclerView = view.findViewById(R.id.market_recycle);
        marketRecycleAdapter = new MarketRecycleAdapter(getContext(), lstMarketProductList);
        market_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        market_recyclerView.setAdapter(marketRecycleAdapter);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstMarketProductList = new ArrayList<>();

    }
}