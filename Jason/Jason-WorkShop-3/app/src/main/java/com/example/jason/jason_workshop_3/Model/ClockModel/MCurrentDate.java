package com.example.jason.jason_workshop_3.Model.ClockModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jason on 16/06/2016.
 */
public class MCurrentDate {
    private MClockDate mClockDate;
    private MClockTime mClockTime;
    private long mCountMilliseconds;
    public MCurrentDate(){
        getCurrentdate();
    }
    public long getmCountMilliseconds() {
        return mCountMilliseconds;
    }

    public void setmCountMilliseconds(long mCountMilliseconds) {
        this.mCountMilliseconds = mCountMilliseconds;
    }

    public MClockDate getmClockDate() {
        return mClockDate;
    }

    public void setmClockDate(MClockDate mClockDate) {
        this.mClockDate = mClockDate;
    }

    public MClockTime getmClockTime() {
        return mClockTime;
    }

    public void setmClockTime(MClockTime mClockTime) {
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
            setmClockDate(new MClockDate(_day,_date,_month,_year));
            long milliseconds = _hour * 60 * 60 + _minute * 60 + _second;
            setmClockTime(new MClockTime(milliseconds, _second, _minute, _hour));
            setmCountMilliseconds(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
