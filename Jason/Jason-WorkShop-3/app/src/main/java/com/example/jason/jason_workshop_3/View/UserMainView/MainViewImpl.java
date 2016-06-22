package com.example.jason.jason_workshop_3.View.UserMainView;

import android.view.View;

import com.example.jason.jason_workshop_3.Model.ClockModel.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.ClockTime;

/**
 * Created by jason on 13/06/2016.
 */
public interface MainViewImpl {
    void showClock(ClockTime mClockTime);
    void showDate(ClockDate mClockDate);
    void setClockRunnable();
    void runClockCount();
    void LogoutIntent();
    void onclickLogout(View v);
    void startActivitys(Class mClass, String intent);
}
