package com.example.learnmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Navigation Header
    TextView navFullname;
    TextView navEmailadd;
    TextView navUserType;
    RoundedImageView nav_profile;
    String name_txt, firstname, lastname;

    // Dialog
    Dialog Netdialog;

    // Main content
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;

    // stay log in
    SharedPreferences sharedPreferences;
    public static final String fileName ="mypref";
    public static final String key_Email = "email";
    public static final String key_Password = "password";

    String sharedEmail;
    String usertype;

    //RecyclerView

    RecyclerView recyclerView;
    RecyclerView.Adapter marketAdapter;
    List<MarketProductList> marketProductLists;
    RecyclerView.LayoutManager layoutManager;

    // CART
    ImageView cart_bttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(fileName,MODE_PRIVATE);
        String email = sharedPreferences.getString(key_Email,null);
        String password = sharedPreferences.getString(key_Password, null);
        sharedEmail = sharedPreferences.getString("email","");

        recyclerView = findViewById(R.id.recycler);

        // Internet Connection
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // checking the network status
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){

            Netdialog = new Dialog(this);

            Netdialog.show();
            Netdialog.setContentView(R.layout.no_internet_diaglog);
            Netdialog.setCanceledOnTouchOutside(false);
            Netdialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            Netdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Netdialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            Netdialog.setCancelable(true);

            Button retryBtn = Netdialog.findViewById(R.id.retryBtn);

            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });

        }
        // checking the network status (no internet connection)

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.Viewpager);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView= findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        //

        // Navigation Header
        View headerView = navigationView.getHeaderView(0);
        navFullname = (TextView) headerView.findViewById(R.id.nav_fullname);
        navEmailadd = (TextView) headerView.findViewById(R.id.nav_emailAdd);
        navUserType = (TextView) headerView.findViewById(R.id.UserType);
        nav_profile = headerView.findViewById(R.id.nav_profile);

        ViewProfile();
        GetAllProducts();
        navEmailadd.setText(email);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toggle.syncState();

        GetUsertype();

        // CART

        cart_bttn = findViewById(R.id.cart_bttn);

        cart_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Cart.class);
                startActivity(intent);
            }
        });

    }

    // Navigation bar onclick activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.themarketplace){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }

        else if (item.getItemId() == R.id.tutorFinder2){
            Intent intent = new Intent(this, TutorFinder.class);
            startActivity(intent);
            finish();

        }
        else if (item.getItemId() == R.id.userProfile){
                if(usertype.equals("Tutor")){
                    Intent intent = new Intent(getApplicationContext(),Tutor_admin.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                    startActivity(intent);
                    finish();
                }
        }

        else if (item.getItemId() == R.id.purchase){
            Intent intent = new Intent(getApplicationContext(),Purchase.class);
            startActivity(intent);
            finish();
        }

        else if(item.getItemId() == R.id.pending){
            Intent intent = new Intent(getApplicationContext(),User_pending_transaction.class);
            startActivity(intent);
            finish();
        }

        else if (item.getItemId() == R.id.dashboard){
                if(usertype.equals("Tutor")){
                    Intent intent = new Intent(getApplicationContext(),Tutor_admin.class);
                    startActivity(intent);
                    finish();
                }
                else if(usertype.equals("Seller")){
                    Intent intent = new Intent(getApplicationContext(),SellerProfile.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),DashBoard.class);
                    startActivity(intent);
                    finish();
                }
        }

        else if (item.getItemId() == R.id.aboutUs){
            Intent intent = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent);
            finish();
        }

        else if (item.getItemId() == R.id.logout){
            Intent intent = new Intent(this, MainActivity.class);
            sharedPreferences.edit().remove("email").apply();
            startActivity(intent);
            finish();
        }

        return false;
    }

//    @Override
//    public void onBackPressed() {
//        Netdialog.dismiss();
//    }

    public void ViewProfile(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_profile.php","POST",field,data);

                if(putData.startPut()){
                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                firstname = object.getString("firstname");
                                lastname = object.getString("lastname");
                                String img = object.getString("image_url");

                                if(!img.equals("")){
                                    String img_url = "http://learn-music.click2drinkph.store/images/"+img;
                                    Glide.with(getApplicationContext()).load(img_url).into(nav_profile);
                                }
                                else {
                                    nav_profile.setImageResource(R.drawable.pic);
                                }


                                name_txt = firstname+" "+lastname;

                                navFullname.setText(name_txt);


                            }

                        } catch (Exception e) {
                            //remove this in production
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error in Internet Connection",Toast.LENGTH_LONG).show();
                }


            }

        });
    }

    public void GetUsertype(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_profile.php","POST",field,data);

                if(putData.startPut()){
                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                 usertype = object.getString("usertype");
                                navUserType.setText("[ "+usertype+" ]");

                            }

                        } catch (Exception e) {
                            //remove this in production
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error in Internet Connection",Toast.LENGTH_LONG).show();
                }


            }

        });
    }

    public void GetAllProducts(){

        layoutManager =new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        marketProductLists = new ArrayList<>();

        String BASE_URL = "http://learn-music.click2drinkph.store/php/GetAllProducts.php";

        String[] field = new String[1];
        field[0] = "email";

        String[] datax = new String[1];
        datax[0] = sharedEmail;

        PutData putData = new PutData(BASE_URL,"POST", field,datax);
        if(putData.startPut()){
            if(putData.onComplete()){
                String result = putData.getResult();

                try {
                    JSONArray array = new JSONArray(result);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        String id = object.getString("id");
                        String seller_email = object.getString("seller_email");
                        String name = object.getString("product_name");
                        String price = object.getString("product_price");
                        String category = object.getString("product_category");
                        String image_url = object.getString("image_url");
                        String url = "http://learn-music.click2drinkph.store/images/"+image_url;

                        MarketProductList data = new MarketProductList(id,seller_email,name,price,category,url);
                        marketProductLists.add(data);
                    }
                }
                catch (Exception e){
                    //CATCH ERROR HERE
                }

                marketAdapter = new MarketRecycleAdapter(Home.this, marketProductLists);
                recyclerView.setAdapter(marketAdapter);

            }
        }


    }
}