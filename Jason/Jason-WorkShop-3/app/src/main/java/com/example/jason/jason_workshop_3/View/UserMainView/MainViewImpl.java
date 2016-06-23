package com.example.jason.jason_workshop_3.View.UserMainView;

import android.view.View;

import com.example.jason.jason_workshop_3.Model.ClockModel.MClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.MClockTime;

/**
 * Created by jason on 13/06/2016.
 */
public interface MainViewImpl {
    void showClock(MClockTime mClockTime);
    void showDate(MClockDate mClockDate);
    void setClockRunnable();
    void runClockCount();
    void LogoutIntent();
    void onclickLogout(View v);
    void startActivitys(Class mClass, String intent);
}
