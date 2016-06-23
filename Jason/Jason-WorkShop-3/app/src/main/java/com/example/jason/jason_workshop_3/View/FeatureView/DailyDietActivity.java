package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckCaloDialog;

public class DailyDietActivity extends AppCompatActivity {

    CheckCaloDialog checkCaloDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_management);
        checkCaloDialog = new CheckCaloDialog(this);
    }

    public void onclickStartBreakfast(View v){
        startActivitys(DietFoodActivity.class, "Breakfast");
    }

    public void onclickStartLunch(View v){
        startActivitys(DietFoodActivity.class, "Lunch");
    }

    public void onclickStartDinner(View v){
        startActivitys(DietFoodActivity.class, "Dinner");
    }

    public void onclickStartSupper(View v){
        startActivitys(DietFoodActivity.class, "Supper");
    }

    public void onclickstartCheckcalo(View v){
        checkCaloDialog.show(1, Gravity.TOP);
    }

    public void startActivitys(Class mClass, String contentItent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("ContentInten", contentItent);
        startActivity(mIntent);
    }
}
