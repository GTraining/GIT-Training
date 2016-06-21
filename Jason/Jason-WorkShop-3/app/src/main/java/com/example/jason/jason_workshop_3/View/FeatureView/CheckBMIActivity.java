package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Presenter.PresentMain.Presenter_CheckBMI;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckBMIAlertDialog;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckBMIResultDialog;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Like Sign up, I also use activity to replace Check BMI MessageDialog.
 */
public class CheckBMIActivity extends AppCompatActivity {

    private EditText edt_age, edt_weight, edt_height;
    private TextView tvHello;
    private String age = "", height = "", weight = "";
    private CheckBMIResultDialog mCheckBMIResultDialog;
    private CheckBMIAlertDialog checkBMIAlertDialog = new CheckBMIAlertDialog(this);
    private int intentNumber;
    private Presenter_CheckBMI mPresenter_checkBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bmi);
        intentNumber = Integer.parseInt(getIntent().getStringExtra("Intent"));
        edt_age = (EditText) findViewById(R.id.editText_age);
        edt_weight = (EditText) findViewById(R.id.editText_weight);
        edt_height = (EditText) findViewById(R.id.editText_height);
        tvHello = (TextView) findViewById(R.id.textView_Hello);
        mCheckBMIResultDialog = new CheckBMIResultDialog(this);
        mPresenter_checkBMI = new Presenter_CheckBMI(this);
        setHelloUser();
    }


    public List<String> getUserHealth(){
        List<String> BMI = new ArrayList<>();
        BMI.add(height);
        BMI.add(weight);
        return BMI;
    }

    public void setHelloUser(){
        tvHello.setText("Hello: " + mPresenter_checkBMI.getCurrenUser() + "!");
    }
    public void startImproveYourHealth(View v){
        mCheckBMIResultDialog.startImproveHealth();
    }

    public void onclickCheckBMI(View v){
        height = edt_height.getText().toString();
        weight = edt_weight.getText().toString();
        age = edt_age.getText().toString();
        if (height.equals("") || weight.equals("") || age.equals(""))
            Toast.makeText(getApplicationContext(), "Something are empty!", Toast.LENGTH_LONG).show();
        else {
            if (check(Float.parseFloat(weight) , Float.parseFloat(height), Integer.parseInt(age)))
                checkBMIAlertDialog.show();
            else mCheckBMIResultDialog.show(1, Gravity.TOP);
        }
    }

    public void onclickCloseActivity(View v){
        if (intentNumber == 2){
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

    //Fix Bug ID: JS_016(Set range for input value)
    public boolean check(float weight, float height, int age){
         if (weight > 1000 || height > 300 || age > 200){
             return true;
         }
        else return false;
    }
    //Fix Bug ID: JS_018(Crash happens when clicking on “X” button on BMI result page)
    //Because System can't find event onclickCloseDialog(View v) in this activity
    public void onclickCloseDialog(View v){
        mCheckBMIResultDialog.dismissDialog();
    }

}
