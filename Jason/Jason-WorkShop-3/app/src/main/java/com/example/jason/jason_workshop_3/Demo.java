package com.example.jason.jason_workshop_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        String a = "012345";
        Toast.makeText(Demo.this, "" + a.substring(0,1), Toast.LENGTH_SHORT).show();
    }
}
