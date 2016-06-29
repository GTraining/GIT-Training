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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DrinkAlarmActivity extends AppCompatActivity {

    private TextView tvAlarmHour_Start, tvAlarmMinute_Start;
    private TextView tvAlarmHour_End, tvAlarmMinute_End;
    private int alarmHourStart = 6, alarmMinuteStart = 0, minStartHour = 6, maxStartHour = 11;
    private int alarmHourEnd = 0, alarmMinuteEnd = 0, minEndHour = 0, maxEndHour = 11;
    private long startTime = 0, endTime = 0, period = 0;
    private Switch enableSwitch;
    private PAlarmSetting alarmSetting;
    private boolean alarmEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_alarm);
        tvAlarmHour_Start = (TextView) findViewById(R.id.textView_hour_start);
        tvAlarmMinute_Start = (TextView) findViewById(R.id.textView_minute_start);
        tvAlarmHour_End = (TextView) findViewById(R.id.textView_hour_end);
        tvAlarmMinute_End = (TextView) findViewById(R.id.textView_minute_end);
        enableSwitch = (Switch) findViewById(R.id.switch_enableAlarm);
        alarmSetting = new PAlarmSetting(this);
        alarmSetting.SetupView();
    }

    /**
     *
     * Set start time onclick
     * @param v
     */

    public void onclickStartHourDown(View v){
        if (alarmHourStart > minStartHour) alarmHourStart --;
        setTvAlarmHour_Start();
    }

    public void onclickStartHourUp(View v){
        if (alarmHourStart < maxStartHour) alarmHourStart ++;
        setTvAlarmHour_Start();
    }

    public void onclickStartMinuteDown(View v){
        if (alarmMinuteStart > 0 ) alarmMinuteStart --;
        else {
            if (alarmHourStart > minStartHour) {
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
            if (alarmHourStart < maxStartHour) {
                alarmHourStart ++;
                alarmMinuteStart = 0;
            }
        }
        setTvAlarmMinute_Start();
        setTvAlarmHour_Start();
    }

    /**
     * Set end time onclick
     * @param v
     */

    public void onclickEndHourDown(View v){
        if (alarmHourEnd > minEndHour) alarmHourEnd --;
        setTvAlarmHour_End();
    }

    public void onclickEndHourUp(View v){
        if (alarmHourEnd < maxEndHour) alarmHourEnd ++;
        setTvAlarmHour_End();
    }

    public void onclickEndMinuteDown(View v){
        if (alarmMinuteEnd > 0 ) alarmMinuteEnd --;
        else {
            if (alarmHourEnd > minEndHour) {
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
            if (alarmHourEnd < maxEndHour) {
                alarmHourEnd ++;
                alarmMinuteEnd = 0;
            }
        }
        setTvAlarmMinute_End();
        setTvAlarmHour_End();
    }

    /**
     * Save Alarm
     * @param v
     */

    public void onclickSaveAlarm(View v){
        String enableAlarm = null;
        if (enableSwitch.isChecked()){
            enableAlarm = "1";
            alarmEnable = true;
        } else {
            alarmEnable = false;
            enableAlarm = "0";
        }

        startTime = convertMilliseconds(String.format("%02d:%02d", alarmHourStart, alarmMinuteStart));
        endTime = convertMilliseconds(String.format("%02d:%02d", alarmHourEnd, alarmMinuteEnd));
        period = (endTime - startTime) / 10;

        Alarm alarm = new Alarm(String.valueOf(startTime), String.valueOf(endTime),
                String.valueOf(period), enableAlarm);
        alarmSetting.SettingAlarm(getAlarm(alarm));

        Toast.makeText(getApplicationContext(), "Completed!", Toast.LENGTH_LONG).show();
        setAlarm();
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
// Start activities
    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }

    public void setupTextView(int startHour, int startMinute, int endHour, int endMinute, String status){
        tvAlarmHour_Start.setText(String.format("%02d", startHour));
        tvAlarmMinute_Start.setText(String.format("%02d", startMinute));
        tvAlarmHour_End.setText(String.format("%02d", endHour));
        tvAlarmMinute_End.setText(String.format("%02d", endMinute));
        if (status.equals("1")) enableSwitch.setChecked(true);
        else enableSwitch.setChecked(false);
    }

    public Alarm getAlarm(Alarm alarm){
        return alarm;
    }

    public void setAlarm(){
        Intent mIntent = new Intent(this, AlarmReceiver.class);
        mIntent.putExtra("PERIOD", String.valueOf(period));
        mIntent.putExtra("START", String.valueOf(startTime));
        mIntent.putExtra("END", String.valueOf(endTime));
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, 0);
        if (alarmEnable) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), period, pendingIntent);
        }else alarmManager.cancel(pendingIntent);
    }

    public long convertMilliseconds(String time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }
}
