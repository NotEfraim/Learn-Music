package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TutorFinder extends AppCompatActivity {

    private RecyclerView FinderRecycleView;
    private List<TutorGetProfile> lstprofile;//
    private List<TutorGetDetails> lstdetails; //

    public static String  profileURL = "https://learn-music.click2drinkph.store/php/view_tutorprofile2.php"; //
    public static String detailsURL = "https://learn-music.click2drinkph.store/php/Display_tutorDetails.php";//


    SharedPreferences sharedPreferences;
    String SP_ID;
    String sharedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_finder);

        getTutorInfo();

        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id", "");
        sharedEmail = sharedPreferences.getString("email","");

    }

    public void getTutorInfo(){

        lstprofile = new ArrayList<>();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = SP_ID;

                PutData putData = new PutData(profileURL, "POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);

                            for(int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");
                                String firstname = object.getString("firstname");
                                String lastname = object.getString("lastname");
                                String age = object.getString("age");
                                String gender = object.getString("gender");
                                String mobileNumber = object.getString("mobile_number");
                                String emailAddress = object.getString("email");
                                String house_no  = object.getString("house_number");
                                String brgy = object.getString("barangay");
                                String municipality = object.getString("municipality");
                                String zipcode = object.getString("zipcode");
                                String img = object.getString("image_url");

                                String location = brgy+" "+municipality;
                                String address = house_no+" "+ brgy +"\n"+municipality+" "+zipcode;
                                String fullName = firstname+" "+lastname;

                                TutorGetProfile getProfile = new TutorGetProfile(id,fullName,age,gender,mobileNumber,
                                        emailAddress,location,address,img);

                                lstprofile.add(getProfile);
                            }

                        }
                        catch(Exception e){
//                             Toast.makeText(getActivity(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                        getTutorInstrument();// displaying the instruments in the view details

                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void getTutorInstrument(){

        lstdetails = new ArrayList<>();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String [1];
                field[0] = "tutor_email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData(detailsURL,"POST",field,data);

                if(putData.startPut()){

                    if(putData.onComplete()){
                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);

                            for(int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String email = object.getString("tutor_email");
                                String instrument = object.getString("instrument");
                                String proficiency = object.getString("proficiency");
                                String fee = object.getString("fee");
                                String sunday = object.getString("sunday");
                                String monday = object.getString("monday");
                                String tuesday = object.getString("tuesday");
                                String wednesday = object.getString("wednesday");
                                String thursday = object.getString("thursday");
                                String friday = object.getString("friday");
                                String saturday = object.getString("saturday");
                                String timeAm = object.getString("timeAm");
                                String timePm = object.getString("timePm");

                                String time = timeAm + " - " + timePm;

                                TutorGetDetails getDetails = new TutorGetDetails(email, instrument, proficiency,
                                        fee, sunday, monday, tuesday, wednesday, thursday, friday, saturday,
                                        time);

                                lstdetails.add(getDetails);

                            }

                        } catch(Exception e){
//                            Toast.makeText(getContext(),SP_ID, Toast.LENGTH_LONG).show();
                        }

                        FinderRecycleView = (RecyclerView) findViewById(R.id.finder_recycleview);
                        RecycleViewAdapter recyclerAdapter = new RecycleViewAdapter(getApplicationContext(), lstprofile, lstdetails);
                        FinderRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        FinderRecycleView.setAdapter(recyclerAdapter);

                    }

                } else{
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}