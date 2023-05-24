package com.example.learnmusic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tab_tutor_student extends Fragment {

    View view;
    String SP_ID, sharedEmail;
    SharedPreferences sharedPreferences;

    private RecyclerView studentRequestRecycleView;
    private List<TutorRequest_list> lstRequest;
    private List<TutorRequestList_instrument> lstInstrument;
    private Tutor_StudentAdapter tutorRequest_adapter;


    public tab_tutor_student() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SP_ID = sharedPreferences.getString("Logged_user_id", "");
        sharedEmail = sharedPreferences.getString("email","");

        GetStudentList();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_tutor_student, container, false);
        return view;
    }

    public void GetStudentList(){

        lstInstrument = new ArrayList<>();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String Base_url = "http://learn-music.click2drinkph.store/php/getStudentList.php";

                String[] field = new String[1];
                field[0] = "tutor_email";

                String[] data = new String[1];
                data[0] = sharedEmail;

                PutData putData = new PutData(Base_url, "POST", field, data);

                if(putData.startPut()){
                    if(putData.onComplete()){
                        String result = putData.getResult();

                        try {
                            JSONArray array = new JSONArray(result);
                            for(int i = 0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");
                                String student_id = object.getString("student_id");
                                String instrument = object.getString("instrument");
                                String proficiency = object.getString("proficiency");
                                String sunday = object.getString("sunday");
                                String monday = object.getString("monday");
                                String tuesday = object.getString("tuesday");
                                String wednesday = object.getString("wednesday");
                                String thursday = object.getString("thursday");
                                String friday = object.getString("friday");
                                String saturday = object.getString("saturday");

                                TutorRequestList_instrument tutorRequestList_instrument = new TutorRequestList_instrument(
                                        id,student_id, sharedEmail, instrument, proficiency, sunday, monday,tuesday,
                                        wednesday,thursday,friday, saturday);

                                lstInstrument.add(tutorRequestList_instrument);

                                getStudentProfile(student_id);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void getStudentProfile(String student_id){

        lstRequest = new ArrayList<>();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "id";

                String[] data = new String[1];
                data[0] = student_id;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/Student_profile.php", "POST", field, data);

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

                                String address = house_no+" "+ brgy +"\n"+municipality+" "+zipcode;
                                String fullName = firstname+" "+lastname;

                                TutorRequest_list request_list = new TutorRequest_list(fullName, age, gender,
                                        mobileNumber, emailAddress,id, address,img);

                                lstRequest.add(request_list);

                            }


                        } catch(Exception e){
//                             Toast.makeText(getActivity(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }


                        studentRequestRecycleView = (RecyclerView) view.findViewById(R.id.recycler);
                        tutorRequest_adapter = new Tutor_StudentAdapter(getContext(), lstRequest, lstInstrument);
                        studentRequestRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        studentRequestRecycleView.setAdapter(tutorRequest_adapter);

                    }
                } else{
                    Toast.makeText(getActivity(),"Error", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}