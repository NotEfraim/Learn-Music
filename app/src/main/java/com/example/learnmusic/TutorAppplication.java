package com.example.learnmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TutorAppplication extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //URL"S

    private static String USERTYPE_URL ="http://learn-music.click2drinkph.store/php/update_usertype.php";

    // Shared References
    String SP_ID;
    String firstname, lastname, fullname, emailAdd;

    // TextView
    TextView name, email;
    String sharedEmail;

    Button save; // save buttom

    // Fee
    EditText fee;
    String tutorFee;

    // Spinner
    private Spinner spinnerInstrument;
    private Spinner spinnerTimeam;
    private Spinner spinnerTimepm;
    String instrument, timeAm, timePm;

    // Radio Button
    RadioGroup radioGroup;
    RadioButton radioButton;
    String proficiency;

    // CheckBox
    CheckBox sun, mon, tue, wed, thu, fri, sat;
    String sunday ="no", monday="no", tuesday="no", wednesday="no", thursday="no", friday="no", saturday="no";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_appplication);

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);

        ViewProfile();

        SP_ID = sharedPreferences.getString("Logged_user_id", "");
        sharedEmail = sharedPreferences.getString("email", "");

        name = findViewById(R.id.tutorName);
        email = findViewById(R.id.tutorEmail);

        save = findViewById(R.id.saveProfile);

        fee = findViewById(R.id.TutorFee);
        radioGroup = findViewById(R.id.TutorProficiency);

        sun = findViewById(R.id.SundayBox);
        mon = findViewById(R.id.MondayBox);
        tue = findViewById(R.id.TuesdayBox);
        wed = findViewById(R.id.WednesdayBox);
        thu = findViewById(R.id.ThursdayBox);
        fri = findViewById(R.id.FridayBox);
        sat = findViewById(R.id.SaturdayBox);

        // Spinner
        spinnerInstrument = findViewById(R.id.instrument_choice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.instrument,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstrument.setAdapter(adapter);
        spinnerInstrument.setOnItemSelectedListener(this);


        spinnerTimeam = findViewById(R.id.timeAm);
        ArrayAdapter<CharSequence> Amadapter = ArrayAdapter.createFromResource(this, R.array.timeAm,
                android.R.layout.simple_spinner_item);
        Amadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeam.setAdapter(Amadapter);
        spinnerTimeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeAm = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerTimepm = findViewById(R.id.timePm);
        ArrayAdapter<CharSequence> Pmadapter = ArrayAdapter.createFromResource(this, R.array.timePm,
                android.R.layout.simple_spinner_item);
        Pmadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimepm.setAdapter(Pmadapter);
        spinnerTimepm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timePm = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int prof_id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(prof_id);
                proficiency = radioButton.getText().toString();

            }
        });

        sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(sun.isChecked()){
                    sunday = "yes";
                } else {
                    sunday = "no";
                }

            }
        });

        mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(mon.isChecked()){
                    monday = "yes";
                } else {
                    monday = "no";
                }

            }
        });

        tue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(tue.isChecked()){
                    tuesday = "yes";
                } else {
                    tuesday = "no";
                }

            }
        });

        wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(wed.isChecked()){
                    wednesday = "yes";
                } else {
                    wednesday = "no";
                }

            }
        });

        thu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(thu.isChecked()){
                    thursday = "yes";
                } else {
                    thursday = "no";
                }

            }
        });

        fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(fri.isChecked()){
                    friday = "yes";
                } else {
                    friday = "no";
                }

            }
        });

        sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(sat.isChecked()){
                    saturday = "yes";
                } else {
                    saturday = "no";
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tutorFee = fee.getText().toString();
                TutorProfileSaved();
                UpdateUserType();

            }
        });

    }

    public void TutorProfileSaved(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[13];
                field[0] = "tutor_email";
                field[1] = "instrument";
                field[2] = "proficiency";
                field[3] = "fee";
                field[4] = "sunday";
                field[5] = "monday";
                field[6] = "tuesday";
                field[7] = "wednesday";
                field[8] = "thursday";
                field[9] = "friday";
                field[10] = "saturday";
                field[11] = "timeAm";
                field[12] = "timePm";

                String[] data = new String[13];
                data[0] = sharedEmail;
                data[1] = instrument;
                data[2] = proficiency;
                data[3] = tutorFee;
                data[4] = sunday;
                data[5] = monday;
                data[6] = tuesday;
                data[7] = wednesday;
                data[8] = thursday;
                data[9] = friday;
                data[10] = saturday;
                data[11] = timeAm;
                data[12] = timePm;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/Update_TutorProfile.php","POST",field,data);

                if(putData.startPut()){
                    if(putData.onComplete()){

                        String result = putData.getResult();
                        Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Tutor_admin.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error in Internet Connection",Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        instrument = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

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
                                emailAdd = object.getString("email");

                                fullname = firstname+ " " + lastname;

                                name.setText(fullname);
                                email.setText(emailAdd);

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

    public void UpdateUserType(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[2];
                field[0] = "email";
                field[1] = "usertype";

                String[] data = new String[2];
                data[0] =  sharedEmail;
                data[1] = "Tutor";

                PutData putData = new PutData(USERTYPE_URL, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();

                    }
                }

            }
        });

    }





} //close