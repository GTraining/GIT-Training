package com.example.jason.jason_workshop_3.Model.ClockModel;

import com.example.jason.jason_workshop_3.Model.ClockModel.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.ClockTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jason on 16/06/2016.
 */
public class CurrentDate {
    private ClockDate mClockDate;
    private ClockTime mClockTime;
    private long mCountMilliseconds;
    public CurrentDate(){
        getCurrentdate();
    }
    public long getmCountMilliseconds() {
        return mCountMilliseconds;
    }

    public void setmCountMilliseconds(long mCountMilliseconds) {
        this.mCountMilliseconds = mCountMilliseconds;
    }

    public ClockDate getmClockDate() {
        return mClockDate;
    }

    public void setmClockDate(ClockDate mClockDate) {
        this.mClockDate = mClockDate;
    }

    public ClockTime getmClockTime() {
        return mClockTime;
    }

    public void setmClockTime(ClockTime mClockTime) {
        this.mClockTime = mClockTime;
    }

    public void getCurrentdate(){
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
            setmClockDate(new ClockDate(_day,_date,_month,_year));
            long milliseconds = _hour * 60 * 60 + _minute * 60 + _second;
            setmClockTime(new ClockTime(milliseconds, _second, _minute, _hour));
            setmCountMilliseconds(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
