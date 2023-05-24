package com.example.learnmusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class tab_tutor_profile extends Fragment {

    View view;

    SharedPreferences sharedPreferences;
    String SP_ID;

    TextView fullname, age, gender, mobile, address, emailAddress;
    TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday, no_sched;
    TextView instrument, time, proficiency, fee;

    ImageView profile_picture;

    String sharedEmail;

    Button editDetails, editProfile;

    public tab_tutor_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_tutor_profile, container, false);

        editDetails = (Button) view.findViewById(R.id.editDetails);
        editProfile = (Button) view.findViewById(R.id.editInfo);
        profile_picture = view.findViewById(R.id.profile_picture);

        fullname = (TextView) view.findViewById(R.id.Tutor_fullname);
        age = (TextView) view.findViewById(R.id.Tutor_age);
        gender = (TextView) view.findViewById(R.id.Tutor_gender);
        mobile = (TextView) view.findViewById(R.id.Tutor_number);
        address = (TextView) view.findViewById(R.id.Tutor_address);
        emailAddress = (TextView) view.findViewById(R.id.Tutor_email);

        instrument = view.findViewById(R.id.Tutor_Instrument);
        proficiency = view.findViewById(R.id.Tutor_Proficiency);
        fee = view.findViewById(R.id.Fee);
        time = view.findViewById(R.id.Time);

        sunday = view.findViewById(R.id.Sunday);
        monday = view.findViewById(R.id.Monday);
        tuesday = view.findViewById(R.id.Tuesday);
        wednesday = view.findViewById(R.id.Wednesday);
        thursday = view.findViewById(R.id.Thursday);
        friday = view.findViewById(R.id.Friday);
        saturday = view.findViewById(R.id.Saturday);
        no_sched = view.findViewById(R.id.no_sched);


        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TutorAppplication.class);
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserProfileEdit.class);
                startActivity(intent);
            }
        });

        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getProfile();
        getTutorInstrumentDetails();

        sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id", "");
        sharedEmail = sharedPreferences.getString("email", "");

    }

    public void getProfile() {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = SP_ID;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_tutorInfo.php", "POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);
                                String firstname = object.getString("firstname");
                                String lastname = object.getString("lastname");
                                String age_txt = object.getString("age");
                                String gender_txt = object.getString("gender");
                                String mobileNumber = object.getString("mobile_number");
                                String houseNumber = object.getString("house_number");
                                String emailAdd = object.getString("email");
                                String brangay = object.getString("barangay");
                                String municipality = object.getString("municipality");
                                String zipcode = object.getString("zipcode");
                                String img = object.getString("image_url");


                                if(!img.equals("")){
                                    String img_url = "http://learn-music.click2drinkph.store/images/"+img;
                                    Glide.with(getContext()).load(img_url).into(profile_picture);
                                }
                                else {
                                    profile_picture.setImageResource(R.drawable.pic);
                                }

                                String fullname_txt = firstname+" "+lastname;
                                String address_text = houseNumber+" "+ brangay +" " + municipality+"\n"+zipcode;

                                fullname.setText(fullname_txt);
                                age.setText(age_txt);
                                gender.setText(gender_txt);
                                mobile.setText(mobileNumber);
                                address.setText(address_text);
                                emailAddress.setText(emailAdd);

                            }

                            getTutorInstrumentDetails();

                        } catch (Exception e) {
                            //remove this in production
//                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }

            }
        });
    }
    public void getTutorInstrumentDetails(){

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "tutor_email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                //
                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/Display_tutorsInfo.php", "POST", field, data);

                if(putData.startPut()){

                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try{

                            JSONArray array = new JSONArray(result);
                            for(int i = 0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);

                                String Instrument = object.getString("instrument");
                                String Proficiency = object.getString("proficiency");
                                String Fee = object.getString("fee");
                                String Sunday = object.getString("sunday");
                                String Monday = object.getString("monday");
                                String Tuesday = object.getString("tuesday");
                                String Wednesday = object.getString("wednesday");
                                String Thursday = object.getString("thursday");
                                String Friday = object.getString("friday");
                                String Saturday = object.getString("saturday");
                                String TimeAm = object.getString("timeAm");
                                String TimePm = object.getString("timePm");

                                String Time = TimeAm + "-" + TimePm;

                                instrument.setText(Instrument);
                                proficiency.setText(Proficiency);
                                fee.setText(Fee);
                                time.setText(Time);

                                if(!Sunday.equals("yes")){
                                    sunday.setVisibility(View.GONE);
                                }
                                if(!Monday.equals("yes")){
                                    monday.setVisibility(View.GONE);
                                }
                                if(!Tuesday.equals("yes")){
                                    tuesday.setVisibility(View.GONE);
                                }
                                if(!Wednesday.equals("yes")){
                                    wednesday.setVisibility(View.GONE);
                                }
                                if(!Thursday.equals("yes")){
                                    thursday.setVisibility(View.GONE);
                                }
                                if(!Friday.equals("yes")){
                                    friday.setVisibility(View.GONE);
                                }
                                if(!Saturday.equals("yes")){
                                    saturday.setVisibility(View.GONE);
                                }

                            }


                        }catch(Exception e){
//                            Toast.makeText(getContext(),SP_ID, Toast.LENGTH_LONG).show();
                            no_sched.setVisibility(View.VISIBLE);
                            monday.setVisibility(View.GONE);
                            tuesday.setVisibility(View.GONE);
                            wednesday.setVisibility(View.GONE);
                            thursday.setVisibility(View.GONE);
                            friday.setVisibility(View.GONE);
                            saturday.setVisibility(View.GONE);
                            sunday.setVisibility(View.GONE);
                        }


                    }

                } else{
                    Toast.makeText(getActivity(),"Error", Toast.LENGTH_LONG).show();
                }



            }
        });

    }



}