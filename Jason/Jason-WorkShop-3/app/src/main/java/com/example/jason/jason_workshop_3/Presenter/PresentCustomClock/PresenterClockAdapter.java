package com.example.jason.jason_workshop_3.Presenter.PresentCustomClock;

import com.example.jason.jason_workshop_3.Model.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockTime;
import com.example.jason.jason_workshop_3.View.CustomClock;

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
    private long mCountMilliseconds = 0;
    private CustomClock mView;

    public PresenterClockAdapter(CustomClock mView) {
        this.mView = mView;
        getCurrentDateTime();
        mView.showDate(mClockDate);
    }

    @Override
    public void getCurrentDateTime() {
        try {
            DateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss a");
            Date mDate = date.parse(date.format(new Date()));
            int _hour = mDate.getHours();
            int _minute = mDate.getMinutes();
            int _second = mDate.getSeconds();
            Calendar mCalendar = Calendar.getInstance();
            String _date = String.valueOf(mCalendar.get(Calendar.DATE));
            String _month = String.valueOf(mCalendar.get(Calendar.MONTH));
            String _year = String.valueOf(mCalendar.get(Calendar.YEAR));
            String _day = String.valueOf(mCalendar.get(Calendar.DAY_OF_WEEK));
            mClockDate = new ClockDate(_day,_date,_month,_year);
            long milliseconds = _hour * 60 * 60 + _minute * 60 + _second;
            mClockTime = new ClockTime(milliseconds, _second, _minute, _hour);
            mCountMilliseconds = milliseconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getmCountMilliseconds() {
        return mCountMilliseconds;
    }

    public void loadClock(long Millis){
        mClockTime.setMilliseconds(Millis);
        mView.showClock(mClockTime);
    }
}
