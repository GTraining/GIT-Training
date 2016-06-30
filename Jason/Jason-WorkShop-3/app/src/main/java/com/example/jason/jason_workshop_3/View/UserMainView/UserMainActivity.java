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

import com.example.jason.jason_workshop_3.Application.HockeyAppManager;
import com.example.jason.jason_workshop_3.Application.MyApplication;
import com.example.jason.jason_workshop_3.Model.ClockModel.MClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.MClockTime;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PSettingActionbar;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Clock.PClockAdapter;
import com.example.jason.jason_workshop_3.View.FeatureView.Alarm.DrinkAlarmActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.BMIChartActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.DailyDietActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.MonthlyCheckBMIActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.WeeklyFitnessActivity;
import com.example.jason.jason_workshop_3.View.LoginView.TwitterLoginActivity;
import com.example.jason.jason_workshop_3.View.MessageDialog.LogoutAlertDialog;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.LoginView.LoginActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.DailyDrinkActivity;

/**
 * @author jason
 * Management System main of User
 */
public class UserMainActivity extends AppCompatActivity implements MainViewImpl {

    private boolean doubleBackToExitPressedOnce = false;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private PClockAdapter mPresenterClockAdapter;
    private long mCountMillisecond;
    private ProgressBar progressBar_second, progressBar_minute, progressBar_hour;
    private TextView txv_time, txv_Second, txv_date, txv_month, txv_year, txv_day, txv_AP;
    private PSettingActionbar mSettingActionbar;
    private HockeyAppManager hockeyAppManager;

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

        hockeyAppManager = new HockeyAppManager(this);
        mPresenterClockAdapter = new PClockAdapter(this);
        mSettingActionbar = new PSettingActionbar(this);

        mCountMillisecond = mPresenterClockAdapter.getCountMilliseconds();
        setClockRunnable();
        runClockCount();
        hockeyAppManager.checkForUpdates();
    }


    @Override
    protected void onResume() {
        super.onResume();
        hockeyAppManager.checkForCrashes();
        MyApplication.getInstance().trackScreenView("User Main Home Screen");
    }

    @Override
    protected void onPause() {
        super.onPause();
        hockeyAppManager.unregisterManagers();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        hockeyAppManager.unregisterManagers();

    }
    //Show current time on custom clock
    @Override
    public void showClock(MClockTime mClockTime) {
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
    public void showDate(MClockDate mClockDate) {
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

    // Start activity Login after run logout function
    @Override
    public void LogoutIntent() {
        startActivities(LoginActivity.class, "none");
    }

    // Open Logout dialog
    @Override
    public void onclickLogout(View view){
        LogoutAlertDialog mAlertDialog = new LogoutAlertDialog(UserMainActivity.this);
        if (checkIntent()) {
            startActivities(TwitterLoginActivity.class, "none");
        }else mAlertDialog.show();
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
        startActivities(DailyDrinkActivity.class, "none");
    }

    // Open Fitness's Schedule Management Activity
    public void onclickWeeklyFitness(View v){
        startActivities(WeeklyFitnessActivity.class, "none");
    }

    // Open Daily Diet Management Activity
    public void onclickDailyDiet(View v){
        startActivities(DailyDietActivity.class, "none");
    }

    // Open Drink alarm setting
    public void onclickStartDrinkAlarm(View v){
        startActivities(DrinkAlarmActivity.class, "none");
    }

    // Open Check BMI activity
    public void onclickCheckBMI(View view){
        startActivities(MonthlyCheckBMIActivity.class, "2");
    }

    //Open history's check BMI Chart dialog
    public void onclickBMIChart(View v){
        startActivities(BMIChartActivity.class, "none");
    }

    //Start new activity
    @Override
    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(UserMainActivity.this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }

    public boolean checkIntent(){
        String intent = getIntent().getStringExtra("Intent");
        if (intent.equals("Twitter")){
            return true;
        } else return false;
    }
}
