package com.example.learnmusic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MarketRecycleAdapter extends RecyclerView.Adapter<MarketRecycleAdapter.MyViewHolder> {
// adapter for market place

    private static String SELLER_PROFILE = "http://learn-music.click2drinkph.store/php/seller_checkout_details.php";

    private Context mContext;
    private List<MarketProductList> mData;
    SharedPreferences sharedPreferences;
    String user_id;
    String seller_email;
    String product_id;
    String product_name;
    String product_price;
    String product_category;
    String image_url;

    //BUY NOW
    String item_id;
    String seller_email_txt;

    //SHARED PREF
    String sharedEmail;
    String login_user_id;

    //Dialog
    Dialog buyNow_Dialog;
    TextView d_seller_name;
    TextView d_seller_address;
    TextView d_seller_number;
    TextView d_seller_email;
    Button d_checkout;
    TextView d_product_name;
    TextView d_product_price;
    TextView d_product_category;

    //BUYNOW
    String idx;
    String email;
    String b_user_id;
    String prod_id;
    String prod_name;
    String prod_price;
    String prod_category;
    String img_url;

    public MarketRecycleAdapter(Context mContext, List<MarketProductList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.market_products, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        sharedPreferences = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
        sharedEmail = sharedPreferences.getString("email","");
        login_user_id = sharedPreferences.getString("Logged_user_id","");
        final MarketProductList getProduct = mData.get(position);

        item_id = getProduct.getId();
        seller_email_txt = getProduct.getSeller_email();

        holder.tv_instrument_name.setText(mData.get(position).getInstrument_name());
        holder.tv_instrument_price.setText(mData.get(position).getInstrument_price());
        holder.category.setText(getProduct.getInstrument_description());
        Glide.with(mContext).load(getProduct.getImage_url()).into(holder.instrument_img);





        // DIALOG
        //Dialog
        buyNow_Dialog = new Dialog(mContext);
        buyNow_Dialog.setContentView(R.layout.buy_now_dialog);
        buyNow_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        d_seller_name = buyNow_Dialog.findViewById(R.id.seller_name);
        d_seller_email = buyNow_Dialog.findViewById(R.id.seller_email);
        d_seller_address = buyNow_Dialog.findViewById(R.id.seller_address);
        d_seller_number = buyNow_Dialog.findViewById(R.id.seller_number);

        d_product_name = buyNow_Dialog.findViewById(R.id.product_name);
        d_product_price = buyNow_Dialog.findViewById(R.id.product_price);
        d_product_category = buyNow_Dialog.findViewById(R.id.product_category);
        d_checkout = buyNow_Dialog.findViewById(R.id.checkout);




        holder.add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = login_user_id;
                seller_email = getProduct.getSeller_email();
                product_id = getProduct.getId();
                product_name = getProduct.getInstrument_name();
                product_price = getProduct.getInstrument_price();
                product_category = getProduct.getInstrument_description();
                image_url = getProduct.getImage_url();
                AddtoCart();
            }
        });

        holder.buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //BUYNOW
                idx = getProduct.getId();
                email = getProduct.getSeller_email();
                b_user_id = login_user_id;
                prod_id = getProduct.getId();
                prod_name = getProduct.getInstrument_name();
                prod_price = getProduct.getInstrument_price();
                prod_category = getProduct.getInstrument_description();
                img_url = getProduct.getImage_url();
                //INSTRUMENT INFO
                d_product_name.setText(getProduct.getInstrument_name());
                d_product_price.setText(getProduct.getInstrument_price());
                d_product_category.setText(getProduct.getInstrument_description());

                ViewSellerProfile();

            }
        });

        d_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyNow();
            }
        });
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_instrument_name;
        TextView tv_instrument_price;
        ImageView instrument_img; //
        TextView category;
        CardView marketContainer;
        Button buy_now, add_order;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.instrument_category);
            tv_instrument_name = (TextView) itemView.findViewById(R.id.instrument_name);
            tv_instrument_price = (TextView) itemView.findViewById(R.id.instrument_price);
            instrument_img = (ImageView) itemView.findViewById(R.id.instrument_pic); //
            marketContainer = (CardView) itemView.findViewById(R.id.instrumentContainer);
            buy_now = itemView.findViewById(R.id.buy_now);
            add_order = itemView.findViewById(R.id.add_order);

        }
    }

    public void AddtoCart(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String Cart_url = "http://learn-music.click2drinkph.store/php/add_to_cart.php";

                String[] field = new String[7];
                field[0] = "user_id";
                field[1] = "seller_email";
                field[2] = "product_id";
                field[3] = "product_name";
                field[4] = "product_price";
                field[5] = "product_category";
                field[6] = "image_url";

                String[] data = new String[7];
                data[0] = user_id;
                data[1] = seller_email;
                data[2] = product_id;
                data[3] = product_name;
                data[4] = product_price;
                data[5] = product_category;
                data[6] = image_url;

                PutData putData = new PutData(Cart_url, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();
                        Toast.makeText(mContext,result,Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    //BUY NOW

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
                        Intent intent = new Intent(mContext, Home.class);
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

                                d_seller_name.setText(name);
                                d_seller_address.setText(address);
                                d_seller_email.setText(seller_email_txt);
                                d_seller_number.setText(pNumber);




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

//                Toast.makeText(mContext, email+b_user_id+prod_id+prod_name+prod_price+prod_category+img_url+idx,Toast.LENGTH_SHORT).show();

                String[] data = new String[8];
                data[0] = email;
                data[1] = b_user_id;
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
        return mData.size();
    }


}
