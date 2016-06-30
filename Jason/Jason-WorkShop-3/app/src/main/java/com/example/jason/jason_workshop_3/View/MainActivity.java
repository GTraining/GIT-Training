package com.example.jason.jason_workshop_3.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jason.jason_workshop_3.Application.MyApplication;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PLoadMain;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.LoginView.LoginActivity;
import com.example.jason.jason_workshop_3.View.LoginView.TwitterLoginActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

public class MainActivity extends AppCompatActivity {

    private PLoadMain loadMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMain = new PLoadMain(this);
    }

    public void startUserMainActivity(){
        startActivities(UserMainActivity.class, "none");
    }

    public void startLoginActivity(){
        startActivities(LoginActivity.class, "none");
    }

    public void startTwitter(){
        startActivities(TwitterLoginActivity.class, "none");
    }

    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Main Screen");
    }
}
