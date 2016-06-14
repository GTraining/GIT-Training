package com.example.jason.jason_workshop_3.View;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockTime;
import com.example.jason.jason_workshop_3.Presenter.PresenterClockAdapter;
import com.example.jason.jason_workshop_3.R;

public class CustomClock extends AppCompatActivity implements CustomClockImpl{

    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private PresenterClockAdapter mPresenterClockAdapter;
    private long mCountMillisecond;
    private ProgressBar progressBar_second, progressBar_minute, progressBar_hour;
    private TextView txv_time, txv_Second, txv_date, txv_month, txv_year, txv_day, txv_AP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_main);

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
        mCountMillisecond = mPresenterClockAdapter.getmCountMilliseconds();
        setClockRunnable();
        runClockCount();
    }

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
    @Override
    public void showDate(ClockDate mClockDate) {
        txv_day.setText(mClockDate.getDay_of_Week());
        txv_date.setText(mClockDate.getDay());
        txv_month.setText(mClockDate.getMonth());
        txv_year.setText(mClockDate.getYear());
    }

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
}
