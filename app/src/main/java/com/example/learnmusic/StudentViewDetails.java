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

import org.json.JSONArray;
import org.json.JSONObject;

public class StudentViewDetails extends AppCompatActivity {

    TextView studentName, studentAgeGender, studentAddress,
            studentNumber, studentInstrument, studentProficiency, studentEmail;

    String id, column_id;

    Button accept, decline;

    SharedPreferences sharedPreferences;
    String email;

    ImageView studentPicture;

    TextView schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_details);

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


        schedule1 = findViewById(R.id.schedule1);
        schedule2= findViewById(R.id.schedule2);
        schedule3 = findViewById(R.id.schedule3);
        schedule4 = findViewById(R.id.schedule4);
        schedule5 = findViewById(R.id.schedule5);
        schedule6 = findViewById(R.id.schedule6);
        schedule7 = findViewById(R.id.schedule7);

        accept = findViewById(R.id.acceptBtn);
        decline = findViewById(R.id.declineBtn);

        String img = intent.getStringExtra("image_url");
        Glide.with(StudentViewDetails.this).load(img).into(studentPicture);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptStudent();
                Intent intent1 = new Intent(getApplicationContext(),Tutor_admin.class);
                startActivity(intent1);
                finish();

            }
        });

        Gets_studentInfo();
        Get_Info();

    }

    //INSTRUMENT
    public void Gets_studentInfo(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "tutor_email";

                String[] data = new String[1];
                data[0] = email;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/Student_getInfo.php","POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);
                                column_id = object.getString("id");
                                String student_id = object.getString("student_id");
                                String tutor_email = object.getString("tutor_email");
                                String instrument = object.getString("instrument");
                                String proficiency = object.getString("proficiency");
                                String sunday = object.getString("sunday");
                                String monday = object.getString("monday");
                                String tuesday = object.getString("tuesday");
                                String wednesday = object.getString("wednesday");
                                String thursday = object.getString("thursday");
                                String friday = object.getString("friday");
                                String saturday = object.getString("saturday");


                                studentInstrument.setText(instrument);
                                studentProficiency.setText(proficiency);

                                id = student_id;

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

                            }

                        } catch (Exception e) {
                            //remove this in production
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }

                    }

                }

            }
        });

    }

    //PROFILE
    public void Get_Info(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = id;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_studentInfo.php","POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);

                                String Fname = object.getString("firstname");
                                String Lname = object.getString("lastname");
                                String email = object.getString("email");
                                String age = object.getString("age");
                                String gender = object.getString("gender");
                                String mobileNumber = object.getString("mobile_number");
                                String houseNumber = object.getString("house_number");
                                String location = object.getString("barangay");
                                String municipality = object.getString("municipality");
                                String zipcode = object.getString("zipcode");

                                String name = Fname + " " + Lname;
                                String age_gender = age + " | " + gender;
                                String address = houseNumber + " " + location + " " + "\n" + municipality + " " + zipcode;

                                studentName.setText(name);
                                studentAgeGender.setText(age_gender);
                                studentAddress.setText(address);
                                studentEmail.setText(email);
                                studentNumber.setText(mobileNumber);

                            }

                        } catch (Exception e) {
                            //remove this in production
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                }

            }
        });

    }

    public void AcceptStudent(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String base_url ="http://learn-music.click2drinkph.store/php/accept_student.php";

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = column_id;

                PutData putData = new PutData(base_url, "POST", field, data);
                 if(putData.startPut()){
                     if(putData.onComplete()){
                         String result = putData.getResult();
                         Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                     }
                 }

            }
        });
    }


}