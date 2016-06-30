package com.example.jason.jason_workshop_3.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.example.jason.jason_workshop_3.Application.MyApplication;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PLoadMain;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.LoginView.LoginActivity;
import com.example.jason.jason_workshop_3.View.LoginView.TwitterLoginActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import net.hockeyapp.android.metrics.MetricsManager;


public class MainActivity extends AppCompatActivity {

    private PLoadMain loadMain;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_heart);
        loadMain = new PLoadMain(this);
        loadMain.Loading(2000);
    }

    public void startUserMainActivity(){
        MetricsManager.trackEvent("Start main");
        startActivities(UserMainActivity.class, "none");
    }

    public void startLoginActivity(){
        MetricsManager.trackEvent("Start login");
        startActivities(LoginActivity.class, "none");
    }

    public void startTwitter(){
        MetricsManager.trackEvent("Start Twitter login");
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

    public void setImage(int drawable){
        imageView.setBackgroundResource(drawable);
    }

}
