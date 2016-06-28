package com.example.jason.jason_workshop_3.View.FeatureView.Alarm;

import android.database.Cursor;

import java.util.List;

/**
 * Created by jason on 28/06/2016.
 */
public interface AlarmDataImpl {
    long INSERT(String username, Alarm alarm);
    Alarm GETALARM(String user);
    long UPDATEALARM(String username, Alarm alarm);
    Cursor SETUPCURSOR();
    boolean CHECKEXISTED(String user);
}
