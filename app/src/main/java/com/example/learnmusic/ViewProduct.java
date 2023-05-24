package com.example.learnmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewProduct extends AppCompatActivity {

    TextView value;
    int count =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        value = (TextView) findViewById(R.id.value);


    }

    public void increment(View v){
        count++;
        value.setText(""+ count);
    }

    public void decrement(View v){
        if(count <= 0) count = 0;
        else count--;

        value.setText(""+count);
    }


}