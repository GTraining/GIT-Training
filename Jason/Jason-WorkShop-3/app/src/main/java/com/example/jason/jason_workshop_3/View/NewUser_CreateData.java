package com.example.jason.jason_workshop_3.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.jason.jason_workshop_3.Model.UserData.UserHealth;
import com.example.jason.jason_workshop_3.Presenter.PresentCheckUserHealth;
import com.example.jason.jason_workshop_3.R;

public class NewUser_CreateData extends AppCompatActivity {

    private EditText edt_age, edt_weight, edt_height;
    private CheckBox cbx_male, cbx_female;
    private String USex = "None";
    private String UserName = "";
    private PresentCheckUserHealth mPresentCheckUserHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        UserName = getIntent().getStringExtra("Username");
        edt_age = (EditText) findViewById(R.id.editText_age);
        edt_weight = (EditText) findViewById(R.id.editText_weight);
        edt_height = (EditText) findViewById(R.id.editText_height);
        cbx_male = (CheckBox) findViewById(R.id.checkBox_male);
        cbx_female = (CheckBox) findViewById(R.id.checkBox_female);
        mPresentCheckUserHealth = new PresentCheckUserHealth(this);
    }
    public void checkMale(View v){
        cbx_male.setChecked(true);
        cbx_male.setChecked(false);
        USex = "Male";
    }
    public void checkFemale(View v){
        cbx_male.setChecked(false);
        cbx_male.setChecked(true);
        USex = "Female";
    }
    public UserHealth getUserHealth(){
        UserHealth mUserHealth = null;
        double height = Double.parseDouble(edt_height.getText().toString());
        double weight = Double.parseDouble(edt_weight.getText().toString());
        mUserHealth = new UserHealth(UserName, edt_age.getText().toString(),
                USex, height, weight);
        return mUserHealth;
    }
    public void startImproveYourHealth(View v){
        mPresentCheckUserHealth.startImproveHealth();
    }
}
