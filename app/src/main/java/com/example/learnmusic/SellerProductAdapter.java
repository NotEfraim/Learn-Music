package com.example.learnmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;

public class SellerProductAdapter extends RecyclerView.Adapter<SellerProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<MarketProductList> marketProductLists;
    String delete_id;

    public SellerProductAdapter(Context mContext, List<MarketProductList> marketProductLists) {
        this.mContext = mContext;
        this.marketProductLists = marketProductLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.seller_product_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final MarketProductList marketProductList = marketProductLists.get(position);

        holder.product_name.setText(marketProductList.getInstrument_name());
        holder.product_price.setText(marketProductList.getInstrument_price());
        holder.product_category.setText(marketProductList.getInstrument_description());
        Glide.with(mContext).load(marketProductList.getImage_url()).into(holder.product_pic);

        String image_url = marketProductList.getImage_url();
        delete_id = marketProductList.getId();

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,edit_seller_product.class);
                intent.putExtra("id",marketProductList.getId());
                intent.putExtra("product_name",marketProductList.getInstrument_name());
                intent.putExtra("product_price", marketProductList.getInstrument_price());
                intent.putExtra("product_category", marketProductList.getInstrument_description());
                intent.putExtra("image_url", image_url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);


            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteProducts();
            }
        });




    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_price;
        TextView product_category;
        ImageView product_pic;
        CardView container;
        Button edit;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_category = itemView.findViewById(R.id.product_category);
            product_pic = itemView.findViewById(R.id.product_pic);
            container = itemView.findViewById(R.id.product_Container);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);


        }
    }

    public void DeleteProducts(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String Base_url = "http://learn-music.click2drinkph.store/php/delete_product.php";

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = delete_id;

                PutData putData = new PutData(Base_url,"POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){

                        String result = putData.getResult();

                        if(result.equals("Success")){
                            Toast.makeText(mContext,"Product Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext,Seller_products.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                        else{
                            Toast.makeText(mContext,"Operation Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return marketProductLists.size();
    }

}
