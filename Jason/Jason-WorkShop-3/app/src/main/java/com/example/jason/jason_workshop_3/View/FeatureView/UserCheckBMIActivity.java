package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Presenter.PresentMain.PresentCheckUserHealth;
import com.example.jason.jason_workshop_3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jason
 * Create new data of User's health
 */
public class UserCheckBMIActivity extends AppCompatActivity {

    private EditText edt_age, edt_weight, edt_height;
    private String _age = "", _height = "", _weight = "";
    private PresentCheckUserHealth mPresentCheckUserHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_check_bmi);
        edt_age = (EditText) findViewById(R.id.editText_age);
        edt_weight = (EditText) findViewById(R.id.editText_weight);
        edt_height = (EditText) findViewById(R.id.editText_height);
        mPresentCheckUserHealth = new PresentCheckUserHealth(this);
    }


    public List<String> getUserHealth(){
        List<String> BMI = new ArrayList<>();
        BMI.add(_height);
        BMI.add(_weight);
        return BMI;
    }

    public void startImproveYourHealth(View v){
        mPresentCheckUserHealth.startImproveHealth();
    }

    public void checkBMI(View v){
        _height = edt_height.getText().toString();
        _weight = edt_weight.getText().toString();
        _age = edt_age.getText().toString();
        if (_height.equals("") || _weight.equals("") || _age.equals(""))
            Toast.makeText(getApplicationContext(), "Something are empty!", Toast.LENGTH_LONG).show();
        else {
            mPresentCheckUserHealth.show(1, Gravity.TOP);
        }
    }

    public void onclickCloseDialog(View v){
        resetEditText();
        mPresentCheckUserHealth.dismissDialog();
    }

    public void onclickCloseActivity(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    public void resetEditText(){
        edt_age.setText("");
        edt_weight.setText("");
        edt_height.setText("");
    }
}
