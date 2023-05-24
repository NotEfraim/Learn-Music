package com.example.learnmusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    // dialog box
    Button savebtn;

    ProgressDialog savedDialog;

    ImageView back;
    EditText edit_fname, edit_lname, edit_age, edit_gender, edit_number, edit_houseno, edit_barangay, edit_cityM, edit_zipcode;
    private  String age, number, houseno, barangay, cityM, zipcode, gender,fname,lname;
    private Spinner spinner;
    String choice;
    SharedPreferences sharedPreferences;
    String id;
    String email;

    //
     ImageView uploadPic;

    //UPLOADER
    private Bitmap bitmap;
    private int IMG_REQUEST = 21;
    ImageView user_profile_pic;


    private String UPDATE_URL = "https://learn-music.click2drinkph.store/php/UpdateProfile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);


        back = findViewById(R.id.ed_back);
        edit_fname = findViewById(R.id.edit_fname);
        edit_lname = findViewById(R.id.edit_lname);
        edit_age = findViewById(R.id.edit_age);
        edit_number = findViewById(R.id.edit_number);
        edit_houseno = findViewById(R.id.edit_houseno);
        edit_barangay = findViewById(R.id.edit_barangay);
        edit_cityM = findViewById(R.id.edit_cityM);
        edit_zipcode = findViewById(R.id.edit_zipcode);
        savebtn = (Button) findViewById(R.id.save_update);
        uploadPic = findViewById(R.id.uploadPic);
        user_profile_pic = findViewById(R.id.user_profile_pic);


        ViewProfile();

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        id = sharedPreferences.getString("Logged_user_id","");
        email = sharedPreferences.getString("email", "");

        // back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), UserProfile.class);
                startActivity(intent);
                finish();
            }
        });

        // after fill up the following, the save button will update the information in the database
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savedDialog = new ProgressDialog(UserProfileEdit.this);


                lname = edit_lname.getText().toString();
                fname = edit_fname.getText().toString();
                age =  edit_age.getText().toString();
                number = edit_number.getText().toString();
                houseno = edit_houseno.getText().toString();
                barangay = edit_barangay.getText().toString();
                cityM = edit_cityM.getText().toString();
                zipcode = edit_zipcode.getText().toString();

                ViewProfile();

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[10];
                        field[0] = "id";
                        field[1] = "edit_fname";
                        field[2] = "edit_lname";
                        field[3] = "edit_age";
                        field[4] = "edit_gender_spinner";
                        field[5] = "edit_number";
                        field[6] = "edit_houseno";
                        field[7] = "edit_barangay";
                        field[8] = "edit_cityM";
                        field[9] = "edit_zipcode";

                        String[] data = new String[10];
                        data[0] = id;
                        data[1] = fname;
                        data[2] = lname;
                        data[3] = age;
                        data[4] = choice;
                        data[5] = number;
                        data[6] = houseno;
                        data[7] = barangay;
                        data[8] = cityM;
                        data[9] = zipcode;

                        PutData putData = new PutData(UPDATE_URL, "POST",field,data);

                        if(putData.startPut()){

                            if(putData.onComplete()){
                                String result = putData.getResult();
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

                                savedDialog.show();
                                savedDialog.setContentView(R.layout.update_dialog);
                                savedDialog.getWindow().setBackgroundDrawableResource(
                                        android.R.color.transparent
                                );

                                savedDialog.setCancelable(true);
                                UploadImage();
                                Button okayBtn;
                                okayBtn = savedDialog.findViewById(R.id.okayBtn);

                                okayBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getApplicationContext(),Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });



                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error on Internet Connection",Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

        // dropdown-list gender button
        spinner = findViewById(R.id.edit_gender_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        //UPLOAD

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data !=null){

            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                user_profile_pic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void UploadImage(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);

        byte[] image_byte = byteArrayOutputStream.toByteArray();
        String encoded_image = Base64.encodeToString(image_byte, Base64.DEFAULT);
//        Toast.makeText(getApplicationContext(),encoded_image,Toast.LENGTH_SHORT).show();
        Call<ResponsePOJO> call = RetroClientProfile.getInstance().getApi().uploadImage(encoded_image,email);
        call.enqueue(new Callback<ResponsePOJO>() {
            @Override
            public void onResponse(Call<ResponsePOJO> call, Response<ResponsePOJO> response) {
                Toast.makeText(getApplicationContext(), response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                if(response.body().isStatus()){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePOJO> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Network Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void ViewProfile() {

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
                                String usertype = object.getString("usertype");
                                String first_name = object.getString("firstname");
                                String last_name = object.getString("lastname");
                                String img = object.getString("image_url");

                                if(!img.equals("")){
                                    String img_url = "http://learn-music.click2drinkph.store/images/"+img;
                                    Glide.with(getApplicationContext()).load(img_url).into(user_profile_pic);
                                }
                                else {
                                    user_profile_pic.setImageResource(R.drawable.pic);
                                }
                                edit_fname.setText(first_name);
                                edit_lname.setText(last_name);
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

//    private void GetUser(){
//
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                String[] field = new String[1];
//                field[0] = "email";
//
//                String[] data = new String[1];
//                data[0] = email;
//
//                PutData putData = new PutData("https://learn-music.click2drinkph.store/php/view_profile.php","POST",field,data);
//
//                if(putData.startPut()){
//                    if(putData.onComplete()){
//
//                        String result = putData.getResult();
//
//                        try {
//                            //Getting Response form api (Base Url)
//                            JSONArray array = new JSONArray(result);
//                            for (int i = 0; i < array.length(); i++) {
//
//                                JSONObject object = array.getJSONObject(i);
//                                String usertype = object.getString("usertype");
//
//                                if(usertype.equals("Tutor")){
//                                    Intent intent = new Intent(UserProfileEdit.this,TutorAppplication.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                                else if (usertype.equals("User")){
//                                    Intent intent = new Intent(UserProfileEdit.this,UserProfile.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                                else if (usertype.equals("Seller")){
//                                    Intent intent = new Intent(UserProfileEdit.this,SellerProfile.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//
//                            }
//
//                        } catch (Exception e) {
//                            //remove this in production
//                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//
//                        }
//
//                    }
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Error in Internet Connection",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//
//
//
//
//    }

//    @Override
//    public void onBackPressed() {
//        savedDialog.dismiss();
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        choice = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        finish();
    }


}