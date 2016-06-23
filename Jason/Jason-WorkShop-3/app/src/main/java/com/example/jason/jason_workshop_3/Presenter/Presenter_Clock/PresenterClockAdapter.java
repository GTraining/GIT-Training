package com.example.jason.jason_workshop_3.Presenter.Presenter_Clock;

import com.example.jason.jason_workshop_3.Model.ClockModel.MClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.MClockTime;
import com.example.jason.jason_workshop_3.Model.ClockModel.MCurrentDate;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

/**
 * Created by jason on 10/06/2016.
 */
public class PresenterClockAdapter implements PresenterClockImpl {

    private MClockTime mClockTime;
    private MClockDate mClockDate;
    private MCurrentDate mCurrentDate = new MCurrentDate();
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
