package com.example.learnmusic;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Pending_transaction_Adapter extends RecyclerView.Adapter<Pending_transaction_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Pending_transaction_List> mData;
    SharedPreferences sharedPreferences;
    //SHARED PREF
    String sharedEmail;

    public Pending_transaction_Adapter(Context mContext, List<Pending_transaction_List> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.pending_transaction_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        sharedPreferences = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
        sharedEmail = sharedPreferences.getString("email","");

        final Pending_transaction_List getPending = mData.get(position);

        holder.Product_name.setText(mData.get(position).getProduct_name());
        holder.Product_price.setText(mData.get(position).getProduct_price());
        holder.Product_category.setText(mData.get(position).getProduct_category());
        Glide.with(mContext).load(getPending.getImage_url()).into(holder.img_url);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Product_name;
        TextView Product_price;
        TextView Product_category;
        Button viewOrder;
        ImageView img_url;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Product_name = itemView.findViewById(R.id.product_name);
            Product_price = itemView.findViewById(R.id.product_price);
            Product_category = itemView.findViewById(R.id.product_category);
            viewOrder = itemView.findViewById(R.id.view_orders);
            img_url = itemView.findViewById(R.id.product_picture);

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
