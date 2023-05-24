package com.example.learnmusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class Seller_view_order_Adapter extends RecyclerView.Adapter<Seller_view_order_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Seller_view_order_List> lstViewOrders;

    //SHARED PREF
    SharedPreferences sharedPreferences;
    String sharedEmail;

    String id, pName, pPrice, pCategory, bName, bNumber, bAddress, bEmail, bImg;

    String column_id;


    public Seller_view_order_Adapter(Context mContext, List<Seller_view_order_List> lstViewOrders) {
        this.mContext = mContext;
        this.lstViewOrders = lstViewOrders;
    }

    @NonNull
    @Override
    public Seller_view_order_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.pending_transaction_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Seller_view_order_Adapter.MyViewHolder holder, int position) {

        sharedPreferences = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
        sharedEmail = sharedPreferences.getString("email","");

        final Seller_view_order_List order_list = lstViewOrders.get(position);

        holder.product_name.setText(order_list.getProduct_name());
        holder.product_price.setText(order_list.getProduct_price());
        holder.product_category.setText(order_list.getProduct_category());
        Glide.with(mContext).load(order_list.getImage_url()).into(holder.product_picture);



        pName = order_list.getProduct_name();
        pPrice = order_list.getProduct_price();
        pCategory = order_list.getProduct_category();


        holder.product_Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = order_list.getUser_id();



                Intent activity = new Intent(mContext, Seller_pending_transaction.class);
                activity.putExtra("prod_name", order_list.getProduct_name());
                activity.putExtra("prod_price", order_list.getProduct_price());
                activity.putExtra("category", order_list.getProduct_category());
                activity.putExtra("prod_id", order_list.getId());
                activity.putExtra("seller_email", order_list.getSeller_email());
                activity.putExtra("image_url", order_list.getImage_url());

                ViewProfile(activity);


            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView product_price;
        TextView product_category;
        TextView viewOrders;
        ImageView product_picture;
        ConstraintLayout product_Container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_category = itemView.findViewById(R.id.product_category);
            viewOrders = itemView.findViewById(R.id.view_orders);
            product_picture = itemView.findViewById(R.id.product_picture);
            product_Container = itemView.findViewById(R.id.product_Container);


        }
    }

    public void ViewProfile(Intent intent){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = id;

                PutData putData = new PutData("http://learn-music.click2drinkph.store/php/view_profile_by_id.php","POST",field,data);

                if(putData.startPut()){
                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                String fName = object.getString("firstname");
                                String lName = object.getString("lastname");
                                String Number = object.getString("mobile_number");
                                String Email = object.getString("email");
                                String hNumber = object.getString("house_number");
                                String brgy = object.getString("barangay");
                                String muni = object.getString("municipality");
                                String img = object.getString("image_url");

                                bName = fName+" "+lName;
                                bNumber = Number;
                                bEmail = Email;
                                bAddress = hNumber+" "+brgy+" "+muni;
                                bImg = "http://learn-music.click2drinkph.store/images/"+img;

                                intent.putExtra("name", bName);
                                intent.putExtra("number", bNumber);
                                intent.putExtra("address", bAddress);
                                intent.putExtra("email", bEmail);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);



                            }

                        } catch (Exception e) {
                            //remove this in production

                        }

                    }
                }
                else{
                    Toast.makeText(mContext,"Error in Internet Connection",Toast.LENGTH_LONG).show();
                }


            }

        });
    }

    @Override
    public int getItemCount() {
        return lstViewOrders.size();
    }


}
