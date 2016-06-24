package com.example.jason.jason_workshop_3.View.FeatureView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Alarm.AlarmReceiver;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserDatabase;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PMonthlyCheckBMI;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckBMIAlertDialog;
import com.example.jason.jason_workshop_3.View.MessageDialog.CheckBMIResultDialog;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Like Sign up, I also use activity to replace Check BMI MessageDialog.
 */
public class MonthlyCheckBMIActivity extends AppCompatActivity {

    private EditText edt_age, edt_weight, edt_height;
    private TextView tvHello;
    private String age = "", height = "", weight = "";
    private CheckBMIResultDialog mCheckBMIResultDialog;
    private CheckBMIAlertDialog checkBMIAlertDialog = new CheckBMIAlertDialog(this);
    private int intentID;
    private UserDatabase mUserDatabase;
    private PMonthlyCheckBMI mPresenterMonthlyCheckBMI;
    private RelativeLayout layoutBMIchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bmi);
        intentID = Integer.parseInt(getIntent().getStringExtra("Intent"));
        edt_age = (EditText) findViewById(R.id.editText_age);
        edt_weight = (EditText) findViewById(R.id.editText_weight);
        edt_height = (EditText) findViewById(R.id.editText_height);
        tvHello = (TextView) findViewById(R.id.textView_Hello);
        layoutBMIchart = (RelativeLayout) findViewById(R.id.layout_bmi_chart);
        mCheckBMIResultDialog = new CheckBMIResultDialog(this);
        mPresenterMonthlyCheckBMI = new PMonthlyCheckBMI(this);
        mUserDatabase = new UserDatabase(this);
        setHelloUser();
    }


    public List<String> getUserHealth(){
        List<String> BMI = new ArrayList<>();
        BMI.add(age);
        BMI.add(height);
        BMI.add(weight);
        return BMI;
    }

    public void setHelloUser(){
        if (intentID == 1) {
            tvHello.setText(mPresenterMonthlyCheckBMI.setCheckTitle());
        }else if (intentID == 2){
            tvHello.setText("CHECK BMI");
            layoutBMIchart.setVisibility(View.INVISIBLE);
        }else tvHello.setText("MONTHLY CHECK BMI");
    }

    public void onclickCheckBMI(View v){
        height = edt_height.getText().toString();
        weight = edt_weight.getText().toString();
        age = edt_age.getText().toString();
        if (height.equals("") || weight.equals("") || age.equals(""))
            Toast.makeText(getApplicationContext(), "Something are empty!", Toast.LENGTH_LONG).show();
        else {
            if (check(Float.parseFloat(weight) , Float.parseFloat(height), Integer.parseInt(age))) {
                checkBMIAlertDialog.show();
            }
            else {
                setEmptyEditText();
                mCheckBMIResultDialog.show(1, Gravity.TOP);
            }
        }
    }

    public void onclickCloseActivity(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (intentID == 1){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        } else {
            Intent mIntent = new Intent(MonthlyCheckBMIActivity.this, UserMainActivity.class);
            startActivity(mIntent);
        }
    }

    public boolean check(float weight, float height, int age){
         if (weight > 1000 || height > 300 || age > 200){
             return true;
         }
        else return false;
    }

    public void onclickCloseDialog(View v){
        mCheckBMIResultDialog.dismissDialog();
    }

    public int checkIntentID(){
        return intentID;
    }

    public void setEmptyEditText(){
        edt_weight.setText("");
        edt_height.setText("");
        edt_age.setText("");
    }

    public void setCheckBMIAlarm(){
        Intent mIntent = new Intent(this, AlarmReceiver.class);
        mIntent.putExtra("Intent", "Check BMI");
        mIntent.putExtra("ContentText", "Please Check BMI!");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pendingIntent);
    }

    public void onclickstartBMIchart(View v){
        Intent mIntent = new Intent(this, BMIChartActivity.class);
        mIntent.putExtra("Intent", "1");
        startActivity(mIntent);
    }

}
