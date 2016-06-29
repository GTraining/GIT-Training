package com.example.jason.jason_workshop_3.View.FeatureView.Alarm;

import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jason on 28/06/2016.
 */
public class PAlarmSetting {
    private DrinkAlarmActivity mView;
    private AlarmData mAlarmData;
    private PUserManagement mUserManagement;
    private CurrentLogin currentLogin;

    public PAlarmSetting(DrinkAlarmActivity mView) {
        this.mView = mView;
        mAlarmData = new AlarmData(mView);
        mAlarmData.open();
        mUserManagement = new PUserManagement(mView);
        currentLogin = mUserManagement.checkCurrentLogin();

    }

    public void SettingAlarm(Alarm alarm){
        boolean checkExisted = mAlarmData.CHECKEXISTED(currentLogin.getUSERNAME());
        if (checkExisted){
            mAlarmData.UPDATEALARM(currentLogin.getUSERNAME(), alarm);
        } else mAlarmData.INSERT(currentLogin.getUSERNAME(), alarm);

        mAlarmData.close();
        mUserManagement.closeDatabase();
        Toast.makeText(mView, "Completed!", Toast.LENGTH_SHORT).show();
        mView.startActivities(UserMainActivity.class, "none");
    }

    public void SetupView(){
        boolean checkExisted = mAlarmData.CHECKEXISTED(currentLogin.getUSERNAME());
        if (checkExisted){

            Alarm alarm = mAlarmData.GETALARM(currentLogin.getUSERNAME());

            List<String> startTime = convertMilliseconds(Long.parseLong(alarm.getStartTime()));
            List<String> endTime = convertMilliseconds(Long.parseLong(alarm.getEndTime()));

            mView.setupTextView(Integer.parseInt(startTime.get(0)),
                    Integer.parseInt(startTime.get(1)),
                    Integer.parseInt(endTime.get(0)),
                    Integer.parseInt(endTime.get(1)),
                    alarm.getStatus());
        } else
            mView.setupTextView(6, 0, 0, 0, "0");
    }

    public List<String> convertMilliseconds(long milliseconds){
        List<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        String Hour = String.valueOf(calendar.get(Calendar.HOUR));
        String Minute = String.valueOf(calendar.get(Calendar.MINUTE));
        list.add(Hour);
        list.add(Minute);
        return list;
    }
}
