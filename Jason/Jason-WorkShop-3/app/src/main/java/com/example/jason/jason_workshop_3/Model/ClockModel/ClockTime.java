package com.example.jason.jason_workshop_3.Model.ClockModel;

import java.util.concurrent.TimeUnit;

/**
 * Created by jason on 10/06/2016.
 */
public class ClockTime implements ClockTimeImpl {
    public long Milliseconds;
    public int Second;
    public int Minute;
    public int Hour;

    public ClockTime(long milliseconds, int second, int minute, int hour) {
        Milliseconds = milliseconds;
        Second = second;
        Minute = minute;
        Hour = hour;
        TimeConvert();
    }

    public void setMilliseconds(long milliseconds) {
        Milliseconds = milliseconds;
        TimeConvert();
    }

    public void setSecond(int second) {
        Second = second;
    }
    public int getSecond() {
        return Second;
    }

    public int getMinute() {
        return Minute;
    }
    public void setMinute(int minute) {
        Minute = minute;
    }

    public int getHour() {
        if ( Hour > 11)
        return (Hour - 12);
        else return Hour;
    }
    public void setHour(int hour) {
        Hour = hour;
    }

    @Override
    public String getAM_PM() {
        if ( Hour > 11)
            return "PM";
        else return "AM";
    }


    @Override
    public String getTime() {
        String Time_format = "%02d:%02d";
        String _Time = String.format(Time_format, getHour(), getMinute());
        return _Time;
    }

    @Override
    public void TimeConvert() {
        long ss = 0, mm = 0, hh = 0;
        ss = TimeUnit.MILLISECONDS.toSeconds(Milliseconds * 1000) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Milliseconds * 1000));
        mm = TimeUnit.MILLISECONDS.toMinutes(Milliseconds * 1000) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(Milliseconds * 1000));
        hh = TimeUnit.MILLISECONDS.toHours(Milliseconds * 1000);
        setHour((int)hh);
        setMinute((int)mm);
        setSecond((int)ss);
    }

}
