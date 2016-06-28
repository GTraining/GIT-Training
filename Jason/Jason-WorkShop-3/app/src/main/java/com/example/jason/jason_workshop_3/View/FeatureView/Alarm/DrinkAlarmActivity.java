package com.example.jason.jason_workshop_3.View.FeatureView.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.R;

import java.util.Calendar;

public class DrinkAlarmActivity extends AppCompatActivity {

    private TextView tvAlarmHour_Start, tvAlarmMinute_Start;
    private TextView tvAlarmHour_End, tvAlarmMinute_End;
    private int alarmHourStart = 0, alarmMinuteStart = 0;
    private int alarmHourEnd = 0, alarmMinuteEnd = 0;
    private long startTime = 0, endTime = 0, period = 0;
    private Switch enableSwitch;
    private PAlarmSetting alarmSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_alarm);
        tvAlarmHour_Start = (TextView) findViewById(R.id.textView_hour_start);
        tvAlarmMinute_Start = (TextView) findViewById(R.id.textView_minute_start);
        tvAlarmHour_End = (TextView) findViewById(R.id.textView_hour_end);
        tvAlarmMinute_End = (TextView) findViewById(R.id.textView_minute_end);
        enableSwitch = (Switch) findViewById(R.id.switch_enableAlarm);
    }

    public void onclickStartHourDown(View v){
        if (alarmHourStart != 0) alarmHourStart --;
        setTvAlarmHour_Start();
    }
    public void onclickStartHourUp(View v){
        if (alarmHourStart != 23) alarmHourStart ++;
        setTvAlarmHour_Start();
    }
    public void onclickStartMinuteDown(View v){
        if (alarmMinuteStart > 0 ) alarmMinuteStart --;
        else {
            if (alarmHourStart > 0) {
                alarmHourStart --;
                alarmMinuteStart = 59;
            }
        }
        setTvAlarmMinute_Start();
        setTvAlarmHour_Start();
    }
    public void onclickStartMinuteUp(View v){
        if (alarmMinuteStart < 59) alarmMinuteStart ++;
        else {
            if (alarmHourStart < 23) {
                alarmHourStart ++;
                alarmMinuteStart = 0;
            }
        }
        setTvAlarmMinute_Start();
        setTvAlarmHour_Start();
    }

    /**
     *
     * @param v
     */

    public void onclickEndHourDown(View v){
        if (alarmHourEnd != 0) alarmHourEnd --;
        setTvAlarmHour_End();
    }
    public void onclickEndHourUp(View v){
        if (alarmHourEnd != 23) alarmHourEnd ++;
        setTvAlarmHour_End();
    }
    public void onclickEndMinuteDown(View v){
        if (alarmMinuteEnd > 0 ) alarmMinuteEnd --;
        else {
            if (alarmHourEnd > 0) {
                alarmHourEnd --;
                alarmMinuteEnd = 59;
            }
        }
        setTvAlarmMinute_End();
        setTvAlarmHour_End();
    }
    public void onclickEndMinuteUp(View v){
        if (alarmMinuteEnd < 59 ) alarmMinuteEnd ++;
        else {
            if (alarmHourEnd < 23) {
                alarmHourEnd ++;
                alarmMinuteEnd = 0;
            }
        }
        setTvAlarmMinute_End();
        setTvAlarmHour_End();
    }

    public void onclickSaveAlarm(View v){
        String enableAlarm = null;
        if (enableSwitch.isChecked()){
            enableAlarm = "1";
        } else enableAlarm = "0";

        if (alarmHourEnd < alarmHourStart){
            Toast.makeText(getApplicationContext(), "Please! Start(time) must be greater than End(time)!", Toast.LENGTH_LONG).show();
        }else {
            startTime = (1000 * alarmHourStart * alarmMinuteStart * 60);
            endTime = (1000 * alarmHourEnd * alarmMinuteEnd * 60);
            period = (endTime - startTime) / 10;
//            Alarm alarm = new Alarm(String.valueOf(startTime), String.valueOf(endTime),
//                    String.valueOf(period), enableAlarm);
//            alarmSetting = new PAlarmSetting(this, alarm);
//            alarmSetting.SettingAlarm();
            Toast.makeText(getApplicationContext(), "Completed!", Toast.LENGTH_LONG).show();
            setAlarm();
        }
    }


    /**
     *
     * set TextView
     */

    public void setTvAlarmHour_Start() {
        tvAlarmHour_Start.setText(String.format("%02d", alarmHourStart));
    }

    public void setTvAlarmMinute_Start() {
        tvAlarmMinute_Start.setText(String.format("%02d", alarmMinuteStart));
    }

    public void setTvAlarmHour_End() {
        tvAlarmHour_End.setText(String.format("%02d", alarmHourEnd));
    }

    public void setTvAlarmMinute_End() {
        tvAlarmMinute_End.setText(String.format("%02d", alarmMinuteEnd));
    }

    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }

    public void setAlarm(){
        Intent mIntent = new Intent(this, AlarmReceiver.class);
        mIntent.putExtra("PERIOD", String.valueOf(period));
        mIntent.putExtra("START", String.valueOf(startTime));
        mIntent.putExtra("END", String.valueOf(endTime));
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), period, pendingIntent);
    }
}
