package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cart extends AppCompatActivity {

    ImageView back;
    public static final String fileName ="mypref";
    String sharedEmail, login_user_id;
    SharedPreferences sharedPreferences;
    TextView empty_alert;
    FrameLayout buttonNav;
    TextView total;
    String sharedTotal;
    Button checkout_button;

    //CheckoutArray
    String[] array_id;

    private static String FETCH_URL = "http://learn-music.click2drinkph.store/php/fetch_cart.php";

    //RECYCLERVIEW
    RecyclerView recyclerView;
    RecyclerView.Adapter CartAdapter;
    List<Cart_List> cart_list;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);
        sharedPreferences = getSharedPreferences(fileName,MODE_PRIVATE);
        sharedEmail = sharedPreferences.getString("email","");
        login_user_id = sharedPreferences.getString("Logged_user_id","");

        back = findViewById(R.id.back);
        empty_alert = findViewById(R.id.empty_alert);
        buttonNav = findViewById(R.id.buttonNav);
        total = findViewById(R.id.Total);
        checkout_button = findViewById(R.id.checkout_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //
        recyclerView = findViewById(R.id.cart_recycleView);

        ViewCart();
        RefreshView();



    }

    public void refresh(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RefreshView();
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }
    @SuppressLint("SetTextI18n")
    public void RefreshView(){
        sharedTotal = sharedPreferences.getString("cart_price","");
        total.setText(sharedTotal);
        refresh(300);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void ViewCart(){

        cart_list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = login_user_id;

                PutData putData = new PutData(FETCH_URL,"POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);

                            //Para sa pag chekout ng lahat ng products
                            array_id = new String[array.length()];
//                                    String msg = String.valueOf(array.length());
//                                    Toast.makeText(Cart.this,msg,Toast.LENGTH_SHORT).show();
                            //

                            for(int i = 0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");

                                array_id[i] = id;

                                //
                                String user_id = object.getString("user_id");
                                String seller_email = object.getString("seller_email");
                                String product_id = object.getString("product_id");
                                String product_name = object.getString("product_name");
                                String product_price = object.getString("product_price");
                                String product_category = object.getString("product_category");
                                String image_url = object.getString("image_url");

                                Cart_List datax = new Cart_List(id,user_id,seller_email,product_id,
                                        product_name,product_price,product_category,image_url);

                                cart_list.add(datax);

                            }


                        } catch (JSONException e) {
                            empty_alert.setVisibility(View.VISIBLE);
                            buttonNav.setVisibility(View.GONE);

                        }
//                        String x = Arrays.toString(array_id);
//                        Toast.makeText(Cart.this,x,Toast.LENGTH_SHORT).show();
                        CartAdapter = new CartAdapter(Cart.this,cart_list);
                        recyclerView.setAdapter(CartAdapter);

                    }
                }

            }
        });

        CheckoutCartItems();
    }

    public void CheckoutCartItems(){
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i<array_id.length; i++){
                    getCartItem(array_id[i]);
                }
                Intent activity = new Intent(getApplicationContext(),Home.class);
                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                startActivity(activity);

            }
        });
    }

  public void getCartItem(String id){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String BASE_URL = "http://learn-music.click2drinkph.store/php/checkout_carts_items.php";

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = id;

                PutData putData = new PutData(BASE_URL, "POST", field, data);
                 if(putData.startPut()){
                     if(putData.onComplete()){
                         String result = putData.getResult();

                         try {
                             JSONArray array = new JSONArray(result);

                             for (int i = 0; i <array.length(); i++){
                                 JSONObject object = array.getJSONObject(i);

                                 String id = object.getString("id");
                                 String user_id = object.getString("user_id");
                                 String seller_email = object.getString("seller_email");
                                 String product_id = object.getString("product_id");
                                 String product_name = object.getString("product_name");
                                 String product_price = object.getString("product_price");
                                 String product_category = object.getString("product_category");
                                 String image_url = object.getString("image_url");


                                 BuyNow(seller_email,user_id,product_id, product_name,
                                         product_price, product_category, image_url, id, product_id);
                             }


                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }
            }
        });
  }

    public void BuyNow(String email, String user_id, String prod_id, String prod_name,
                       String prod_price, String prod_category, String img_url, String idx, String pID){
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
                data[7] = pID;

                PutData putData = new PutData(BASE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();
                        RemoveToCart(idx);


                    }
                }


            }
        });
    }

    public void RemoveToCart(String item_id){

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
                    }
                }

            }
        });
    }


}