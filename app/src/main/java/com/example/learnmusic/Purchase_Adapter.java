package com.example.learnmusic;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Purchase_Adapter extends RecyclerView.Adapter<Purchase_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Purchase_list> lstpurchase;

    //SHARED PREF
    SharedPreferences sharedPreferences;
    String sharedEmail;

    public Purchase_Adapter(Context mContext, List<Purchase_list> lstpurchase) {
        this.mContext = mContext;
        this.lstpurchase = lstpurchase;
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

        final Purchase_list getPurchase = lstpurchase.get(position);

        holder.product_name.setText(getPurchase.getProduct_name());
        holder.product_price.setText(getPurchase.getProduct_price());
        holder.product_category.setText(getPurchase.getProduct_category());
        Glide.with(mContext).load(getPurchase.getImg_url()).into(holder.img_url);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView product_price;
        TextView product_category;
        ImageView img_url;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_category = itemView.findViewById(R.id.product_category);
            img_url = itemView.findViewById(R.id.product_picture);

        }
    }

    @Override
    public int getItemCount() {
        return lstpurchase.size();
    }



}
