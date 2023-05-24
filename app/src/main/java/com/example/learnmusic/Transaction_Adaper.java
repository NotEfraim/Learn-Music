package com.example.learnmusic;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Transaction_Adaper extends RecyclerView.Adapter<Transaction_Adaper.MyViewHolder>{

    private Context mContext;
    private List<Transaction_history_List> lstHistory;

    public Transaction_Adaper(Context mContext, List<Transaction_history_List> lstHistory) {
        this.mContext = mContext;
        this.lstHistory = lstHistory;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.transaction_history_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Transaction_history_List history_list = lstHistory.get(position);

        holder.product_name.setText(history_list.getProduct_name());
        holder.product_price.setText(history_list.getProduct_price());
        holder.product_category.setText(history_list.getProduct_category());
        holder.buyer_name.setText(history_list.getBuyer_name());
        holder.buyer_email.setText(history_list.getBuyer_email());
        Glide.with(mContext).load(history_list.getImg_url()).into(holder.img_url);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView product_price;
        TextView product_category;
        TextView buyer_name;
        TextView buyer_email;
        ImageView img_url;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_category = itemView.findViewById(R.id.product_category);
            buyer_name = itemView.findViewById(R.id.buyer_name);
            buyer_email = itemView.findViewById(R.id.buyer_email);
            img_url = itemView.findViewById(R.id.product_picture);

        }
    }


    @Override
    public int getItemCount() {
        return lstHistory.size();
    }
}