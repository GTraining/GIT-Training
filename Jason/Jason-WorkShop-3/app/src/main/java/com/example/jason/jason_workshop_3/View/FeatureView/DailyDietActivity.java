package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckCaloDialog;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

public class DailyDietActivity extends AppCompatActivity {

    CheckCaloDialog checkCaloDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_management);
        checkCaloDialog = new CheckCaloDialog(this);
    }

    public void onclickStartBreakfast(View v){
        startActivities(FoodActivity.class, "Breakfast");
    }

    public void onclickStartLunch(View v){
        startActivities(FoodActivity.class, "Lunch");
    }

    public void onclickStartDinner(View v){
        startActivities(FoodActivity.class, "Dinner");
    }

    public void onclickStartSupper(View v){
        startActivities(FoodActivity.class, "Supper");
    }

    public void onclickStartCheckCalo(View v){
        checkCaloDialog.show(1, Gravity.TOP);
    }

    public void onclickBack(View v){
        startActivities(UserMainActivity.class, "none");
    }

    public void onclickCloseDialog(View v){
        checkCaloDialog.dismissDialog();
    }

    public void startActivities(Class mClass, String contentItent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("ContentInten", contentItent);
        startActivity(mIntent);
    }


}
