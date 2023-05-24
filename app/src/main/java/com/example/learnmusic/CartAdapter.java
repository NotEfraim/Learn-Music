package com.example.learnmusic;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private static String SELLER_PROFILE = "http://learn-music.click2drinkph.store/php/seller_checkout_details.php";

    private Context mContext;
    private List<Cart_List> cart_list;
    String item_id;
    int[] price_holder;
    int final_price = 0;
    SharedPreferences sharedPreferences;

    //Dialog
    Dialog buyNow_Dialog;
    TextView seller_name;
    TextView seller_address;
    TextView seller_number;
    TextView seller_email;
    Button checkout;
    String seller_email_txt;
    TextView product_name;
    TextView product_price;
    TextView product_category;

    //VARIABLE
    String idx;
    String email;
    String user_id;
    String prod_id;
    String prod_name;
    String prod_price;
    String prod_category;
    String img_url;


    public CartAdapter(Context mContext, List<Cart_List> cart_list){
         this.mContext = mContext;
         this.cart_list = cart_list;


    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cart_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {

        sharedPreferences = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
        final Cart_List cart_lists = cart_list.get(position);
        Glide.with(mContext).load(cart_lists.image_url).into(holder.instrumentPic);
        holder.InstrumentName.setText(cart_lists.product_name);
        holder.price.setText(cart_lists.getProduct_price());
        item_id = cart_lists.getId();

        price_holder = new int[cart_list.size()];
        price_holder[position] = Integer.parseInt(cart_lists.product_price);
        final_price = final_price + price_holder[position];

        //VARIABLES
        idx = cart_lists.getProduct_id();
        email = cart_lists.getSeller_email();
        user_id = cart_lists.getUser_id();
        prod_id = cart_lists.getId();
        prod_name = cart_lists.getProduct_name();
        prod_price = cart_lists.getProduct_price();
        prod_category = cart_lists.getProduct_category();
        img_url = cart_lists.getImage_url();



        if(position == cart_list.size()-1){
            sharedPreferences.edit().putString("cart_price",String.valueOf(final_price)).apply();
        }


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveToCart();
            }
        });

        //Dialog
        buyNow_Dialog = new Dialog(mContext);
        buyNow_Dialog.setContentView(R.layout.buy_now_dialog);
        buyNow_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        seller_name = buyNow_Dialog.findViewById(R.id.seller_name);
        seller_email = buyNow_Dialog.findViewById(R.id.seller_email);
        seller_address = buyNow_Dialog.findViewById(R.id.seller_address);
        seller_number = buyNow_Dialog.findViewById(R.id.seller_number);

        product_name = buyNow_Dialog.findViewById(R.id.product_name);
        product_price = buyNow_Dialog.findViewById(R.id.product_price);
        product_category = buyNow_Dialog.findViewById(R.id.product_category);
        checkout = buyNow_Dialog.findViewById(R.id.checkout);

        holder.buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seller_email_txt = cart_lists.getSeller_email();
                ViewSellerProfile();

                // DIALOG INSTRUMENT
                product_name.setText(cart_lists.getProduct_name());
                product_price.setText(cart_lists.getProduct_price());
                product_category.setText(cart_lists.getProduct_category());

            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyNow();
            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView instrumentPic;
        TextView InstrumentName;
        TextView price;
        Button remove, buy_now;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            instrumentPic = itemView.findViewById(R.id.instrumentPic);
            InstrumentName = itemView.findViewById(R.id.InstrumentName);
            price = itemView.findViewById(R.id.price);
            remove = itemView.findViewById(R.id.remove);
            buy_now = itemView.findViewById(R.id.buy_now);

        }
    }

    public void RemoveToCart(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String REMOVE_URL ="http://learn-music.click2drinkph.store/php/remove_to_cart.php";

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = item_id;

                PutData putData = new PutData(REMOVE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();
                        Intent intent = new Intent(mContext, Cart.class);
                        mContext.startActivity(intent);
                        ((Activity)mContext).finish();
                    }
                }

            }
        });
    }

    public void ViewSellerProfile(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = seller_email_txt;

                PutData putData = new PutData(SELLER_PROFILE, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();

                        try {

                            JSONArray array = new JSONArray(result);
                            for(int i=0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String first_name = object.getString("firstname");
                                String lastname = object.getString("lastname");
                                String emailx = object.getString("email");
                                String pNumber = object.getString("mobile_number");
                                String hNumber = object.getString("house_number");
                                String brgy = object.getString("barangay");
                                String municipality = object.getString("municipality");

                                String address = hNumber+" "+brgy+" "+municipality;
                                String name = first_name+" "+lastname;

                                seller_name.setText(name);
                                seller_address.setText(address);
                                seller_email.setText(seller_email_txt);
                                seller_number.setText(pNumber);


                                buyNow_Dialog.show();
                                buyNow_Dialog.setCancelable(true);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });

    }

    public void BuyNow(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String BASE_URL = "http://learn-music.click2drinkph.store/php/buy_now.php";

                String[] field = new String[8];
                field[0] = "seller_email";
                field[1] = "user_id";
                field[2] = "product_id";
                field[3] = "product_name";
                field[4] = "product_price";
                field[5] = "product_category";
                field[6] = "image_url";
                field[7] = "id";


                String[] data = new String[8];
                data[0] = email;
                data[1] = user_id;
                data[2] = prod_id;
                data[3] = prod_name;
                data[4] = prod_price;
                data[5] = prod_category;
                data[6] = img_url;
                data[7] = idx;

                PutData putData = new PutData(BASE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();
                        Toast.makeText(mContext,result,Toast.LENGTH_SHORT).show();
                        Intent activity = new Intent(mContext,Home.class);
                        RemoveToCart();
                        mContext.startActivity(activity);
                        ((Activity)mContext).finish();
                    }
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return cart_list.size();
    }
}
