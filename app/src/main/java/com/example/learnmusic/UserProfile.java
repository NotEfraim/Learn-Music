package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class UserProfile extends AppCompatActivity {

    TextView fullname, age, gender, mobileNumber, emailAddress, address;
    Button editBtn;
    ImageView back;

    ImageView edit, edit2, edit3, edit4, edit5, edit6;

    SharedPreferences sharedPreferences;
    String email;

    //
    ImageView temp_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Ito ay way para kunin ang data sa shared preferences
        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        email = sharedPreferences.getString("email","");

        fullname = findViewById(R.id.fullname);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        mobileNumber = findViewById(R.id.mobileNumber);
        emailAddress = findViewById(R.id.emailAddress);
        address = findViewById(R.id.address);
        back = findViewById(R.id.back2);
        temp_image = findViewById(R.id.temp_image);

        // edit buttons
        editBtn = findViewById(R.id.editBtn);
        edit = findViewById(R.id.Edit);
        edit2 = findViewById(R.id.Edit2);
        edit3 = findViewById(R.id.Edit3);
        edit4 = findViewById(R.id.Edit4);
        edit5 = findViewById(R.id.Edit5);
        edit6 = findViewById(R.id.Edit6);

        ViewProfile();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        edit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (UserProfile.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void ViewProfile(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = email;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_profile.php","POST",field,data);

                if(putData.startPut()){
                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);
                                String first_name = object.getString("firstname");
                                String last_name = object.getString("lastname");
                                String age_txt = object.getString("age");
                                String gender_txt = object.getString("gender");
                                String mobile_number_txt = object.getString("mobile_number");
                                String house_number_txt = object.getString("house_number");
                                String brgy_txt = object.getString("barangay");
                                String municipality_txt = object.getString("municipality");
                                String zipcode_txt = object.getString("zipcode");
                                String img = object.getString("image_url");

                                if(!img.equals("")){
                                    String img_url = "http://learn-music.click2drinkph.store/images/"+img;
                                    Glide.with(getApplicationContext()).load(img_url).into(temp_image);
                                }
                                else {
                                   temp_image.setImageResource(R.drawable.pic);
                                }

                                String fullname_txt = first_name+" "+last_name;
                                String address_text = house_number_txt+" "+brgy_txt+" "+" "+municipality_txt+" "+","+zipcode_txt;

                                fullname.setText(fullname_txt);
                                emailAddress.setText(email);

                                if (age_txt.equals("null")){
                                    age.setText(" ");
                                    gender.setText(" ");
                                    mobileNumber.setText(" ");
                                    address.setText(" ");
                                }
                                else {
                                    age.setText(age_txt);
                                    gender.setText(gender_txt);
                                    mobileNumber.setText(mobile_number_txt);
                                    address.setText(address_text);
                                }

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

    public void EditProfile(){
        Intent intent = new Intent(UserProfile.this, UserProfileEdit.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        finish();
    }

}