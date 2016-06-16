package com.example.jason.jason_workshop_3.Presenter.PresentCustomClock;

import com.example.jason.jason_workshop_3.Model.ClockModel.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.ClockTime;
import com.example.jason.jason_workshop_3.Model.ClockModel.CurrentDate;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jason on 10/06/2016.
 */
public class PresenterClockAdapter implements PresenterClockImpl {

    private ClockTime mClockTime;
    private ClockDate mClockDate;
    private CurrentDate mCurrentDate = new CurrentDate();
    private long mCountMilliseconds = 0;
    private UserMainActivity mView;

    public PresenterClockAdapter(UserMainActivity mView) {
        this.mView = mView;
        mClockDate = mCurrentDate.getmClockDate();
        mClockTime = mCurrentDate.getmClockTime();
        mCountMilliseconds = mCurrentDate.getmCountMilliseconds();
        mView.showDate(mClockDate);
    }


    public long getCountMilliseconds() {
        return mCountMilliseconds;
    }

    public void loadClock(long Millis){
        mClockTime.setMilliseconds(Millis);
        mView.showClock(mClockTime);
    }
}
