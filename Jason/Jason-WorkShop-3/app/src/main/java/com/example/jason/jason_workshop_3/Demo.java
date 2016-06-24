package com.example.jason.jason_workshop_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;

public class Demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        UserDatabase mUserDatabase = new UserDatabase(this);
        mUserDatabase.open();
        mUserDatabase.UpdateHealthStatus("3", "alala");
        UserCheckInfo mUserCheckInfo = mUserDatabase.checkUser("llll");
        Toast.makeText(Demo.this, "" + mUserCheckInfo.getStatus(), Toast.LENGTH_SHORT).show();
        mUserDatabase.close();
    }
}
