package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.Handler;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_up extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText sign_fname, sign_lname, sign_email;
    TextInputEditText sign_password, sign_confirm_password;
    Button btnsignup;
    TextView login_text;
    ImageView back;
    SharedPreferences sharedPreferences;
    private String id, sharedpref_email;
    private String first_name, last_name, email_address, password,confirmpassword;

    // Spinner
    private Spinner spinnerUsertype; //
    String Usertype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);

        sign_fname = findViewById(R.id.sign_fname);
        sign_lname = findViewById(R.id.sign_lname);
        sign_email = findViewById(R.id.sign_email);
        sign_password = findViewById(R.id.sign_password);
        sign_confirm_password = findViewById(R.id.sign_confirm_password);
        btnsignup = findViewById(R.id.btnsignup);
        login_text = findViewById(R.id.login_text);
        back = findViewById(R.id.back);

        spinnerUsertype = findViewById(R.id.userType); //
        ArrayAdapter<CharSequence> useradapter = ArrayAdapter.createFromResource(this, R.array.user_type,
                android.R.layout.simple_spinner_item);
        useradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsertype.setAdapter(useradapter);
        spinnerUsertype.setOnItemSelectedListener(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first_name = sign_fname.getText().toString();
                last_name = sign_lname.getText().toString();
                email_address =sign_email.getText().toString();
                password = sign_password.getText().toString();
                confirmpassword = sign_confirm_password.getText().toString();

                if (!first_name.equals("") && !last_name.equals("") && !email_address.equals("") && !password.equals("")) {

                    if(isEmailValid(email_address)){

                        if (password.equals(confirmpassword)) {
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    String[] field = new String[5];
                                    field[0] = "firstname";
                                    field[1] = "lastname";
                                    field[2] = "email";
                                    field[3] = "password";
                                    field[4] = "usertype"; //

                                    String[] data = new String[5];
                                    data[0] = first_name;
                                    data[1] = last_name;
                                    data[2] = email_address;
                                    data[3] = password;
                                    data[4] = Usertype; //

                                    PutData putData = new PutData("https://learn-music.click2drinkph.store/php/signup.php", "POST", field, data);

                                    if (putData.startPut()) {

                                        if (putData.onComplete()) {
                                            String result = putData.getResult();

                                            if (result.equals("Sign Up Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                                sharedPreferences.edit().putString("email",email_address).apply();
                                                ViewProfile();

                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                        Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    }

                }
        });


    }

    public boolean isEmailValid(String email)
    {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void ViewProfile(){

        sharedpref_email = sharedPreferences.getString("email","");

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                String[] field = new String[1];
                field[0] = "email";

                String[] data = new String[1];
                data[0] = sharedpref_email;

                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_profile.php","POST",field,data);

                if(putData.startPut()){
                    if(putData.onComplete()){

                        String result = putData.getResult();

                        try {
                            //Getting Response form api (Base Url)
                            JSONArray array = new JSONArray(result);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                id = object.getString("id");
//                                String usertype = object.getString("usertype");

                                sharedPreferences.edit().putString("Logged_user_id",id).apply();
                                //to login activity

                                Intent intent = new Intent(getApplicationContext(), UserProfileEdit.class);
                                startActivity(intent);
                                finish();

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


    @Override
    public void onItemSelected(AdapterView<?> useradpater, View view, int position, long id) {
        Usertype = useradpater.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}