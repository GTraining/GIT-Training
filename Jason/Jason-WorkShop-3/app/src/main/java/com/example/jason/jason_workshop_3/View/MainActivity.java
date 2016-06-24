package com.example.jason.jason_workshop_3.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.Presenter_LoadMain;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.LoginView.LoginActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

public class MainActivity extends AppCompatActivity {

    private Presenter_LoadMain loadMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMain = new Presenter_LoadMain(this);
    }

    public void startUserMainActivity(){
        startActivities(UserMainActivity.class);
    }

    public void startLoginActivity(){
        startActivities(LoginActivity.class);
    }

    public void startActivities(Class mClass){
        Intent mIntent = new Intent(this, mClass);
        startActivity(mIntent);
    }
}
