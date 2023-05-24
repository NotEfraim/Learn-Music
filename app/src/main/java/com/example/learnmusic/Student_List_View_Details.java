package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Student_List_View_Details extends AppCompatActivity {

    TextView studentName, studentAgeGender, studentAddress,
            studentNumber, studentInstrument, studentProficiency, studentEmail;

    String id, column_id;


    SharedPreferences sharedPreferences;
    String email;

    ImageView studentPicture;

    TextView schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7;

    String DELETE_URL = "http://learn-music.click2drinkph.store/php/delete_accepted_student.php";
    Button delete;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__list__view__details);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        email = sharedPreferences.getString("email","");

        Intent intent = getIntent();

        studentName = findViewById(R.id.student_name);
        studentAgeGender = findViewById(R.id.student_age_gender);
        studentAddress = findViewById(R.id.student_address);
        studentNumber = findViewById(R.id.student_number);
        studentInstrument = findViewById(R.id.student_instrument);
        studentProficiency = findViewById(R.id.student_proficiency);
        studentEmail = findViewById(R.id.student_email);
        studentPicture = findViewById(R.id.studentPicture);
        delete = findViewById(R.id.delete);


        schedule1 = findViewById(R.id.schedule1);
        schedule2= findViewById(R.id.schedule2);
        schedule3 = findViewById(R.id.schedule3);
        schedule4 = findViewById(R.id.schedule4);
        schedule5 = findViewById(R.id.schedule5);
        schedule6 = findViewById(R.id.schedule6);
        schedule7 = findViewById(R.id.schedule7);

        //PROFILE
        id = intent.getStringExtra("id");
        String img = intent.getStringExtra("image_url");
        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");
        String gender = intent.getStringExtra("gender");
        String number = intent.getStringExtra("mobileNumber");
        String email = intent.getStringExtra("emailAddress");
        String address =  intent.getStringExtra("address");

        studentName.setText(name);
        studentAgeGender.setText(age+"|"+gender);
        studentNumber.setText(number);
        studentEmail.setText(email);
        studentAddress.setText(address);
        //Instruments

        String tutorEmail =  intent.getStringExtra("tutor_email");
        String instrument =  intent.getStringExtra("instrument");
        String proficiency = intent.getStringExtra("proficiency");
        String sunday =  intent.getStringExtra("sunday");
        String monday =  intent.getStringExtra("monday");
        String tuesday =  intent.getStringExtra("tuesday");
        String wednesday =  intent.getStringExtra("wednesday");
        String thursday =  intent.getStringExtra("thursday");
        String friday =  intent.getStringExtra("friday");
        String saturday =  intent.getStringExtra("saturday");

        studentProficiency.setText(proficiency);


        if(!sunday.equals("") && !sunday.equals("yes") && !sunday.equals("no")){
            schedule7.setText("Sunday "+sunday);
            schedule7.setVisibility(View.VISIBLE);
        }
        if(!monday.equals("") && !monday.equals("yes") && !monday.equals("no")){
            schedule1.setText("Monday "+monday);
            schedule1.setVisibility(View.VISIBLE);
        }
        if(!tuesday.equals("") && !tuesday.equals("yes") && !tuesday.equals("no")){
            schedule2.setText("Tuesday "+tuesday);
            schedule2.setVisibility(View.VISIBLE);
        }
        if(!wednesday.equals("") && !wednesday.equals("yes") && !wednesday.equals("no")){
            schedule3.setText("Wednesday "+wednesday);
            schedule3.setVisibility(View.VISIBLE);
        }
        if(!thursday.equals("") && !thursday.equals("yes") && !thursday.equals("no")){
            schedule4.setText("Thursday "+thursday);
            schedule4.setVisibility(View.VISIBLE);
        }
        if(!friday.equals("") && !friday.equals("yes") && !friday.equals("no")){
            schedule5.setText("Friday "+thursday);
            schedule5.setVisibility(View.VISIBLE);
        }
        if(!saturday.equals("") && !saturday.equals("yes") && !saturday.equals("no")){
            schedule6.setText("Saturday "+saturday);
            schedule6.setVisibility(View.VISIBLE);
        }

        Glide.with(getApplicationContext()).load(img).into(studentPicture);

        DeleteStudent();

    }

    public void DeleteStudent(){

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "id";

                        String[] data = new String[1];
                        data[0] = id;

                        PutData putData = new PutData(DELETE_URL, "POST", field, data);

                        if(putData.startPut()){
                            if(putData.onComplete()){
                                String result = putData.getResult();
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Tutor_admin.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });

            }
        });
    }
}