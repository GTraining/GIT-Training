package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckBMIAlertDialog;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckBMIResultDialog;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import java.util.ArrayList;
import java.util.List;

public class CheckBMIActivity extends AppCompatActivity {

    private EditText edt_age, edt_weight, edt_height;
    private String _age = "", _height = "", _weight = "";
    private CheckBMIResultDialog mCheckBMIResultDialog;
    private CheckBMIAlertDialog checkBMIAlertDialog = new CheckBMIAlertDialog(this);
    private int intent_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bmi);
        intent_number = Integer.parseInt(getIntent().getStringExtra("Intent"));
        edt_age = (EditText) findViewById(R.id.editText_age);
        edt_weight = (EditText) findViewById(R.id.editText_weight);
        edt_height = (EditText) findViewById(R.id.editText_height);
        mCheckBMIResultDialog = new CheckBMIResultDialog(this);
    }


    public List<String> getUserHealth(){
        List<String> BMI = new ArrayList<>();
        BMI.add(_height);
        BMI.add(_weight);
        return BMI;
    }

    public void startImproveYourHealth(View v){
        mCheckBMIResultDialog.startImproveHealth();
    }

    public void onclickCheckBMI(View v){
        _height = edt_height.getText().toString();
        _weight = edt_weight.getText().toString();
        _age = edt_age.getText().toString();
        if (_height.equals("") || _weight.equals("") || _age.equals(""))
            Toast.makeText(getApplicationContext(), "Something are empty!", Toast.LENGTH_LONG).show();
        else {
            if (check(Float.parseFloat(_weight) ,Float.parseFloat(_height)))
                checkBMIAlertDialog.show();
            else mCheckBMIResultDialog.show(1, Gravity.TOP);
        }
    }

    public void onclickCloseActivity(View v){
        if (intent_number == 2){
            Intent mIntent = new Intent(CheckBMIActivity.this, UserMainActivity.class);
            startActivity(mIntent);
        }else onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    public boolean check(float weight, float height){
         if (weight > 1000){
             edt_weight.setText("");
             return true;
         }
        if (height > 300){
            edt_weight.setText("");
            return true;
        }
        else return false;
    }

}
