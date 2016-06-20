package com.example.jason.jason_workshop_3.View.UserMainView;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.ClockModel.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.ClockTime;
import com.example.jason.jason_workshop_3.Presenter.PresentCustomClock.PresentSettingActionbar;
import com.example.jason.jason_workshop_3.Presenter.PresentCustomClock.PresenterClockAdapter;
import com.example.jason.jason_workshop_3.View.FeatureView.CheckBMIActivity;
import com.example.jason.jason_workshop_3.View.MessageDialog.LogoutAlertDialog;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.LoginView.LoginActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.WaterDrinkingActivity;
import com.example.jason.jason_workshop_3.View.MessageDialog.UserBMIChartBar;

/**
 * @author jason
 * Management System main of User
 */
public class UserMainActivity extends AppCompatActivity implements MainViewImpl {

    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private PresenterClockAdapter mPresenterClockAdapter;
    private long mCountMillisecond;
    private ProgressBar progressBar_second, progressBar_minute, progressBar_hour;
    private TextView txv_time, txv_Second, txv_date, txv_month, txv_year, txv_day, txv_AP;
    private PresentSettingActionbar mSettingActionbar;
    private UserBMIChartBar mUserBMIChartBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);

        progressBar_hour = (ProgressBar) findViewById(R.id.progressBar_Hours);
        progressBar_minute = (ProgressBar) findViewById(R.id.progressBar_Minute);
        progressBar_second = (ProgressBar) findViewById(R.id.progressBar_Seconds);
        txv_time = (TextView) findViewById(R.id.textView_time);
        txv_Second = (TextView) findViewById(R.id.textView_second);
        txv_year = (TextView) findViewById(R.id.textView_year);
        txv_month = (TextView) findViewById(R.id.textView_month);
        txv_date = (TextView) findViewById(R.id.textView_date);
        txv_day = (TextView) findViewById(R.id.textView_day);
        txv_AP = (TextView) findViewById(R.id.textView_Ap);

        mPresenterClockAdapter = new PresenterClockAdapter(this);
        mSettingActionbar = new PresentSettingActionbar(this);


        mCountMillisecond = mPresenterClockAdapter.getCountMilliseconds();
        setClockRunnable();
        runClockCount();
    }


    //Show current time on custom clock
    @Override
    public void showClock(ClockTime mClockTime) {
        int hour = 0, minute = 0, second = 0;
        String format_seconds = "%02d", AM_PM = "";
        AM_PM = mClockTime.getAM_PM();
        hour = mClockTime.getHour();
        minute = mClockTime.getMinute();
        second = mClockTime.getSecond();
        String seconds = String.format(format_seconds, second);
        progressBar_hour.setProgress(hour * 60 * 60 + minute * 60 + second);
        progressBar_minute.setProgress(minute * 60 + second);
        progressBar_second.setProgress(second);
        txv_time.setText(mClockTime.getTime());
        txv_AP.setText(AM_PM);
        txv_Second.setText(seconds);
    }

    //Show current date on custom clock
    @Override
    public void showDate(ClockDate mClockDate) {
        txv_day.setText(mClockDate.getDay_of_Week());
        txv_date.setText(mClockDate.getDay());
        txv_month.setText(mClockDate.getMonth());
        txv_year.setText(mClockDate.getYear());
    }

    //Set Runnable for customclock
    @Override
    public void setClockRunnable() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mPresenterClockAdapter.loadClock(mCountMillisecond);
                mCountMillisecond ++;
                mHandler.postDelayed(this, 1000);
            }
        };
    }

    @Override
    public void runClockCount() {
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    // Start activity Login after run logout function
    @Override
    public void LogoutIntent() {
        doOpenActivity(LoginActivity.class);
    }

    // Open Logout dialog
    @Override
    public void onclickLogout(View view){
        LogoutAlertDialog mAlertDialog = new LogoutAlertDialog(UserMainActivity.this);
        mAlertDialog.show();
    }

    // Open setting actionbar
    public void onclickOpenSetting(View v){
        mSettingActionbar.show(1, Gravity.TOP);
    }

    // Close setting actionbar
    public void onClickCloseSetting(View v){
        mSettingActionbar.dismissDialog();
    }

    // Open Water Management Activity
    public void onclickDailyDrink(View v){
        doOpenActivity(WaterDrinkingActivity.class);
    }

    // Open Fitness's Schedule Management Activity
    public void onclickDailyFitness(View v){
        Toast.makeText(UserMainActivity.this, "Developing!", Toast.LENGTH_SHORT).show();
    }

    // Open Daily Diet Management Activity
    public void onclickDailyDiet(View v){
        Toast.makeText(UserMainActivity.this, "Developing!", Toast.LENGTH_SHORT).show();
    }

    // Open Statistic Chart Activity
    public void onclickStatisticChart(View v){

        Toast.makeText(UserMainActivity.this, "Developing!", Toast.LENGTH_SHORT).show();
    }

    // Open Check BMI activity
    public void onclickCheckBMI(View view){
        Intent mIntent = new Intent(UserMainActivity.this, CheckBMIActivity.class);
        mIntent.putExtra("Intent", "2");
        
        startActivity(mIntent);
    }

    //Open history's check BMI Chart dialog
    public void onclickBMIChart(View v){
        mUserBMIChartBar = new UserBMIChartBar(this);
        mUserBMIChartBar.show(1, Gravity.CENTER);
    }

    //Close history's check BMI Chart dialog
    public void onclickCloseChart(View v){
        mUserBMIChartBar.dismissDialog();
    }

    //Start new activity
    @Override
    public void doOpenActivity(Class mClass){
        Intent mIntent = new Intent(UserMainActivity.this, mClass);
        startActivity(mIntent);
    }
}
