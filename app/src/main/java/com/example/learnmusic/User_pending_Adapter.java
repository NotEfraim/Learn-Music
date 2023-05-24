package com.example.learnmusic;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class User_pending_Adapter extends RecyclerView.Adapter<User_pending_Adapter.MyViewHolder> {

    private Context mContext;
    private List<User_pending_list> lstuser_pending;
    String Seller_email;
    String f_name, l_name, Name, Number, Address, Email;
    public User_pending_Adapter(Context mContext, List<User_pending_list> lstuser_pending) {
        this.mContext = mContext;
        this.lstuser_pending = lstuser_pending;
    }


    @NonNull
    @Override
    public User_pending_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.user_pending_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_pending_Adapter.MyViewHolder holder, int position) {

        final User_pending_list user_pending_list = lstuser_pending.get(position);
        holder.product_price.setText(user_pending_list.getProduct_price());
        holder.product_category.setText(user_pending_list.getProduct_category());
        Glide.with(mContext).load(user_pending_list.getImage_url()).into(holder.img_url);


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, View_user_pending_transaction.class);
                intent.putExtra("id", user_pending_list.getId());
                intent.putExtra("prod_name", user_pending_list.getProduct_name());
                intent.putExtra("prod_price",user_pending_list.getProduct_price());
                intent.putExtra("prod_category", user_pending_list.getProduct_category());
                intent.putExtra("image_url", user_pending_list.getImage_url());

                Seller_email = user_pending_list.getSeller_email();
                ViewSellerProfile(Seller_email, intent);

            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_name;
        TextView product_price;
        TextView product_category;
        ImageView img_url;
        ConstraintLayout container;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_category = itemView.findViewById(R.id.product_category);
            img_url = itemView.findViewById(R.id.product_picture);
            container = itemView.findViewById(R.id.product_Container);


        }
    }

    public void ViewSellerProfile(String seller_email, Intent intent){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String BASE_URL = "http://learn-music.click2drinkph.store/php/view_profile.php";
                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = seller_email;

                PutData putData = new PutData(BASE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();

                        try {

                            JSONArray array = new JSONArray(result);
                            for(int i=0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String first_name = object.getString("firstname");
                                String lastname = object.getString("lastname");
                                Email = object.getString("email");
                                Number = object.getString("mobile_number");
                                String hNumber = object.getString("house_number");
                                String brgy = object.getString("barangay");
                                String municipality = object.getString("municipality");

                                Address = hNumber+" "+brgy+" "+municipality;
                                Name = first_name+" "+lastname;

                                intent.putExtra("sName", Name);
                                intent.putExtra("sNumber", Number);
                                intent.putExtra("sAddress", Address);
                                intent.putExtra("sEmail", Email);

                                mContext.startActivity(intent);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return lstuser_pending.size();
    }


}
