package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

public class TutorViewDetails extends AppCompatActivity {

    ImageView back;
    TextView name, age, email, location, instrument, proficiency;
    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    TextView timeSchedule, fee;
    Button inquire;

    Intent newActivity;

    Intent intent;
    //

    String image_url;
    ImageView tv_image;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_view_details);

        back = findViewById(R.id.tv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorViewDetails.this, TutorFinder.class);
                startActivity(intent);
                finish();

            }
        });

        name = findViewById(R.id.tv_tutorName);
        age = findViewById(R.id.tv_age);
        email = findViewById(R.id.tv_email);
        location = findViewById(R.id.tv_location);
        instrument = findViewById(R.id.tv_instrument);
        proficiency = findViewById(R.id.tv_proficiency);

        sunday = findViewById(R.id.tv_sunday);
        monday = findViewById(R.id.tv_monday);
        tuesday = findViewById(R.id.tv_tuesday);
        wednesday = findViewById(R.id.tv_wednesday);
        thursday = findViewById(R.id.tv_thursday);
        friday = findViewById(R.id.tv_friday);
        saturday = findViewById(R.id.tv_saturday);
        tv_image = findViewById(R.id.tv_image);

        timeSchedule = findViewById(R.id.tv_timeSched);
        fee = findViewById(R.id.tv_fee);

        intent = getIntent();


        String name_txt = intent.getStringExtra("name");
        String age_txt = intent.getStringExtra("age");
        String gender_txt = intent.getStringExtra("gender");
        String address_txt = intent.getStringExtra("address");
        String location_txt = intent.getStringExtra("location");
        String email_txt = intent.getStringExtra("email");
        String mobileNumber_txt = intent.getStringExtra("mobileNumber");
        image_url = intent.getStringExtra("image_url");

        //
        Glide.with(TutorViewDetails.this).load(image_url).into(tv_image);

        String instrument_txt = intent.getStringExtra("instrument");
        String proficiency_txt = intent.getStringExtra("proficiency");
        String fee_txt = intent.getStringExtra("fee");
        String sunday_txt = intent.getStringExtra("sunday");
        String monday_txt = intent.getStringExtra("monday");
        String tuesday_txt = intent.getStringExtra("tuesday");
        String wednesday_txt = intent.getStringExtra("wednesday");
        String thursday_txt = intent.getStringExtra("thursday");
        String friday_txt = intent.getStringExtra("friday");
        String saturday_txt = intent.getStringExtra("saturday");
        String timeAm_txt = intent.getStringExtra("timeAm");
        String timePm_txt = intent.getStringExtra("timePm");

        String time_txt = timeAm_txt;

        name.setText(name_txt);
        age.setText(age_txt + " |");
        email.setText(email_txt);
        location.setText(location_txt);
        instrument.setText(instrument_txt);
        proficiency.setText(proficiency_txt);
        fee.setText("₱" + fee_txt + ".00");
        timeSchedule.setText(time_txt);


        newActivity = new Intent(TutorViewDetails.this, StudentApplicationForm.class);

        if(!sunday_txt.equals("yes")){
            sunday.setVisibility(View.GONE);
            newActivity.putExtra("sunday","no");
        }
        else{
            newActivity.putExtra("sunday","yes");
        }

        if(!monday_txt.equals("yes")){
            monday.setVisibility(View.GONE);
            newActivity.putExtra("monday", "no");
        } else {
            newActivity.putExtra("monday", "yes");
        }

        if(!tuesday_txt.equals("yes")){
            tuesday.setVisibility(View.GONE);
            newActivity.putExtra("tuesday", "no");
        } else {
            newActivity.putExtra("tuesday", "yes");
        }

        if(!wednesday_txt.equals("yes")){
            wednesday.setVisibility(View.GONE);
            newActivity.putExtra("wednesday", "no");
        } else {
            newActivity.putExtra("wednesday", "yes");
        }

        if(!thursday_txt.equals("yes")){
            thursday.setVisibility(View.GONE);
            newActivity.putExtra("thursday", "no");
        } else {
            newActivity.putExtra("thursday", "yes");
        }

        if(!friday_txt.equals("yes")){
            friday.setVisibility(View.GONE);
            newActivity.putExtra("friday", "no");
        } else {
            newActivity.putExtra("friday", "yes");
        }

        if(!saturday_txt.equals("yes")){
            saturday.setVisibility(View.GONE);
            newActivity.putExtra("saturday", "no");
        } else {
            newActivity.putExtra("saturday", "yes");
        }

        newActivity.putExtra("fee", fee_txt);
        newActivity.putExtra("instrument", instrument_txt);
        newActivity.putExtra("timeAm", timeAm_txt);
        newActivity.putExtra("timePm", timePm_txt);
        newActivity.putExtra("email", email_txt);
        newActivity.putExtra("image_url", image_url);


        inquire = findViewById(R.id.tv_inquire);
        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newActivity);
            }
        });


    }
}