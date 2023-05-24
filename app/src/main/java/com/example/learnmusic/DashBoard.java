package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

public class DashBoard extends AppCompatActivity {

    private static String INSERT_URL ="https://learn-music.click2drinkph.store/php/apply_tutor.php";
    private static String VIEW_PROFILE ="http://learn-music.click2drinkph.store/php/view_profile.php";
    String SP_ID;
    String sharedEmail;
    CardView tutor;
    CardView seller;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id","");
        sharedEmail = sharedPreferences.getString("email", "");

        // id
        tutor = findViewById(R.id.be_a_tutor);
        seller = findViewById(R.id.be_a_seller);

        //FUNCTION
        CheckUserType();

        back = findViewById(R.id.dash_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DashBoard.this, Home.class);
                startActivity(intent);
            }
        });

        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DashBoard.this, TutorAppplication.class);
                startActivity(intent);
                finish();
            }
        });


        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this, SellerProfile.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void CheckUserType() {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData(VIEW_PROFILE, "POST", field, data);

                if (putData.startPut()) {

                    if (putData.onComplete()) {

                        String result = putData.getResult();

                        try {

                            JSONArray array = new JSONArray(result);
                            for(int i=0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String usertype = object.getString("usertype");

                                if(usertype.equals("Tutor")){
                                    tutor.setVisibility(View.GONE);
                                }
                                else if (usertype.equals("Seller")){
                                    seller.setVisibility(View.GONE);
                                }

                            }

                        }
                        catch (Exception e){

                            //HANDLE ERROR

                        }

                    }

                }


            }
        });

    }


}