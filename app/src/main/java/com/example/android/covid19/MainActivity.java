package com.example.android.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenCases(View View){
        Intent intent = new Intent(this,IndiaCases.class);
        startActivity(intent);
    }

    public void OpenVaccine(View View){
        Intent intent = new Intent(this,Vaccine.class);
        startActivity(intent);
    }
}