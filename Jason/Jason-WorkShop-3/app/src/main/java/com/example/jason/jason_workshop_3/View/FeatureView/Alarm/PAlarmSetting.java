package com.example.jason.jason_workshop_3.View.FeatureView.Alarm;

import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import java.util.ArrayList;
import java.util.Calendar;
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
            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(Long.parseLong(alarm.getStartTime()));
            String startHour = String.valueOf(calendar.get(Calendar.HOUR));
            String startMinute = String.valueOf(calendar.get(Calendar.MINUTE));

            calendar.setTimeInMillis(Long.parseLong(alarm.getEndTime()));
            String endHour = String.valueOf(calendar.get(Calendar.HOUR));
            String endMinute = String.valueOf(calendar.get(Calendar.MINUTE));

            mView.setupTextView(startHour, startMinute, endHour, endMinute, alarm.getStatus());
        } else mView.setupTextView("06", "00", "00", "00", "0");
    }
}
