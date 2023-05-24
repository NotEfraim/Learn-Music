package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.Struct;
import java.util.Calendar;

public class StudentApplicationForm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Back btn
    ImageView back;
    TextView instrument, fee, email, S_time;
    String date_holder;

    // dialog
    Dialog confirmDialog;
    Button confirm;

    // Submit btn
    Button submit;

    //radio button
    RadioGroup radioGroup;
    RadioButton radioButton;
    String student_proficiency, st_email;

    // Check Box
    CheckBox mon, tue, wed, thu, fri, sat, sun;

//    String a,b,c,d,e,f,g,h,i,j,k,l;
    String instrument_txt, fee_text, tutor_email;
    String mon_txt ="no", tue_txt="no", wed_txt="no", thu_txt="no", fri_txt="no", sat_txt="no", sun_txt="no";
    String timeAm, timePm;

    String Shared_ID; // string for shared references;

    Intent intent;

    //
    ImageView tutor_profile;

    //Data picker
    TextView monday_date, tuesday_date, wednesday_date, thursday_date, friday_date, saturday_date,sunday_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_application_form);

        // user_id
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        Shared_ID= sharedPreferences.getString("Logged_user_id","");

        // submit btn
        submit = findViewById(R.id.application_submit);

        // back button
        back = findViewById(R.id.back);

        // radio button id
        radioGroup = findViewById(R.id.student_radio);

        mon = findViewById(R.id.monday);
        tue = findViewById(R.id.tuesday);
        wed = findViewById(R.id.wednesday);
        thu = findViewById(R.id.thursday);
        fri = findViewById(R.id.friday);
        sat = findViewById(R.id.saturday);
        sun = findViewById(R.id.sunday);
        fee = findViewById(R.id.st_fee);
        tutor_profile = findViewById(R.id.tutor_profile);
        instrument = findViewById(R.id.st_instrument);
        email = findViewById(R.id.st_email);
        S_time = findViewById(R.id.st_time);
        monday_date = findViewById(R.id.monday_date);
        tuesday_date = findViewById(R.id.tuesday_date);
        wednesday_date = findViewById(R.id.wednesday_date);
        thursday_date = findViewById(R.id.thursday_date);
        friday_date = findViewById(R.id.friday_date);
        saturday_date = findViewById(R.id.saturday_date);
        sunday_date = findViewById(R.id.sunday_date);

        intent = getIntent();

        tutor_email = intent.getStringExtra("email");
        fee_text = intent.getStringExtra("fee");
        instrument_txt = intent.getStringExtra("instrument");
        sun_txt = intent.getStringExtra("sunday");
        mon_txt = intent.getStringExtra("monday");
        tue_txt = intent.getStringExtra("tuesday");
        wed_txt = intent.getStringExtra("wednesday");
        thu_txt = intent.getStringExtra("thursday");
        fri_txt = intent.getStringExtra("friday");
        sat_txt = intent.getStringExtra("saturday");
        timeAm = intent.getStringExtra("timeAm");
        String image_url = intent.getStringExtra("image_url");

        String time = timeAm;

        instrument.setText(instrument_txt);
        fee.setText("₱" + fee_text + ".00");
        email.setText(tutor_email);
        S_time.setText(time);
        Glide.with(this).load(image_url).into(tutor_profile);

        if(!sun_txt.equals("yes")){
            sun.setVisibility(View.GONE);
            sunday_date.setVisibility(View.GONE);
        }
        if(!mon_txt.equals("yes")){
            mon.setVisibility(View.GONE);
            monday_date.setVisibility(View.GONE);
        }
        if(!tue_txt.equals("yes")){
            tue.setVisibility(View.GONE);
            tuesday_date.setVisibility(View.GONE);
        }
        if(!wed_txt.equals("yes")){
            wed.setVisibility(View.GONE);
            wednesday_date.setVisibility(View.GONE);
        }
        if(!thu_txt.equals("yes")){
            thu.setVisibility(View.GONE);
            thursday_date.setVisibility(View.GONE);
        }
        if(!fri_txt.equals("yes")){
            fri.setVisibility(View.GONE);
            friday_date.setVisibility(View.GONE);
        }
        if(!sat_txt.equals("yes")){
            sat.setVisibility(View.GONE);
            saturday_date.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentApplicationForm.this,TutorViewDetails.class);
                startActivity(intent);
                finish();
            }
        });


        //radio selection
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int prof_id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(prof_id);
                student_proficiency = radioButton.getText().toString();
            }
        });

        // radio selection - end

        // schedule

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(2);
                if(date_holder!=null){
                    monday_date.setVisibility(View.VISIBLE);
                    monday_date.setText("Monday Date:"+date_holder);
                    mon_txt = date_holder;
                }

            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DatePicker(3);
                if(date_holder!=null) {
                    tuesday_date.setVisibility(View.VISIBLE);
                    tuesday_date.setText("Tuesday Date:" + date_holder);
                    tue_txt = date_holder;

                }
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     DatePicker(4);
                if(date_holder!=null) {
                    wednesday_date.setVisibility(View.VISIBLE);
                    wednesday_date.setText("Wednesday Date:" + date_holder);
                    wed_txt = date_holder;

                }
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(5);
                if(date_holder!=null) {
                    thursday_date.setVisibility(View.VISIBLE);
                    thursday_date.setText("Thursday Date:" + date_holder);
                    thu_txt = date_holder;

                }
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(6);
                if(date_holder!=null) {
                    friday_date.setVisibility(View.VISIBLE);
                    friday_date.setText("Friday Date:" + date_holder);
                    fri_txt = date_holder;

                }
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(7);
                if(date_holder!=null) {
                    saturday_date.setVisibility(View.VISIBLE);
                    saturday_date.setText("Saturday Date:" + date_holder);
                    sat_txt = date_holder;

                }
            }
        });

        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(1);
                if(date_holder!=null) {
                    sunday_date.setVisibility(View.VISIBLE);
                    sunday_date.setText("Sunday Date:" + date_holder);
                    sun_txt = date_holder;

                }
            }
        });

        // submit
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentApplication();
            }
        });
        // submit - end

    }

    //function
    public void StudentApplication (){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[11];
                field[0] = "student_id";
                field[1] = "instrument";
                field[2] = "proficiency";
                field[3] = "sunday";
                field[4] = "monday";
                field[5] = "tuesday";
                field[6] = "wednesday";
                field[7] = "thursday";
                field[8] = "friday";
                field[9] = "saturday";
                field[10] = "tutor_email";

                String[] data = new String[11];
                data[0] = Shared_ID;
                data[1] = instrument_txt;
                data[2] = student_proficiency;
                data[3] = sun_txt;
                data[4] = mon_txt;
                data[5] = tue_txt;
                data[6] = wed_txt;
                data[7] = thu_txt;
                data[8] = fri_txt;
                data[9] = sat_txt;
                data[10] = tutor_email;


                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/Student_Application.php", "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                        confirmDialog = new Dialog(StudentApplicationForm.this);

                        confirmDialog.show();
                        confirmDialog.setContentView(R.layout.student_application_confirmation);
                        confirmDialog.getWindow().setBackgroundDrawableResource(
                                android.R.color.transparent
                        );

                        confirm = confirmDialog.findViewById(R.id.okBtn);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(StudentApplicationForm.this, Home.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error in Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void onBackPressed(){
        confirmDialog.dismiss();
    }

    public void DatePicker(int val){
        Calendar calendar = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                StudentApplicationForm.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        int appDay = val;


        datePickerDialog = DatePickerDialog.newInstance(StudentApplicationForm.this,Year, Month, Day);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.setTitle("Select Date of Schedule");
        Calendar calendar1 = Calendar.getInstance();
        int today = calendar1.get(Calendar.DAY_OF_WEEK);
        Calendar[] calendars = new Calendar[10];
        if ( appDay == today)
        {
            int update = 0;
            for (int i = 0; i < calendars.length; i++)
            {
                final Calendar calendar2 = Calendar.getInstance();
                calendar2.get(Calendar.DAY_OF_WEEK);
                calendar2.add(Calendar.DATE, update);
                calendars[i] = calendar2;
                update += 7;
            }
        }
        else if ( appDay > today)
        {
            int for1 = appDay - today;
            for (int i = 0; i < calendars.length; i++)
            {
                final Calendar calendar2 = Calendar.getInstance();
                calendar2.get(Calendar.DAY_OF_WEEK);
                calendar2.add(Calendar.DATE, for1);
                calendars[i] = calendar2;
                for1 += 7;
            }
        }
        else if ( appDay < today)
        {
            int for2 = (7-today)+appDay;
            for (int i = 0; i < calendars.length; i++)
            {
                final Calendar calendar2 = Calendar.getInstance();
                calendar2.get(Calendar.DAY_OF_WEEK);
                calendar2.add(Calendar.DATE, for2);
                calendars[i] = calendar2;
                for2 += 7;
            }
        }
        datePickerDialog.setSelectableDays(calendars);
        datePickerDialog.show(getSupportFragmentManager(),"DatePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

            date_holder = +year+"/"+monthOfYear+"/"+dayOfMonth;



    }
} // close