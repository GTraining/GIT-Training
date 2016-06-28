package com.example.jason.jason_workshop_3.View.FeatureView.Alarm;

import com.example.jason.jason_workshop_3.Model.ClockModel.MClockTime;

/**
 * Created by jason on 28/06/2016.
 */
public class Alarm {
    public String StartTime;
    public String EndTime;
    public String Period;
    public String Status;

    public Alarm( String startTime, String endTime, String period, String status) {
        StartTime = startTime;
        EndTime = endTime;
        Period = period;
        Status = status;
    }


    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
