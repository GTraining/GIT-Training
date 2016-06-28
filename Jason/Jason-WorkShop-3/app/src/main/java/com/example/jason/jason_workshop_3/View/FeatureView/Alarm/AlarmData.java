package com.example.jason.jason_workshop_3.View.FeatureView.Alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 28/06/2016.
 */
public class AlarmData implements AlarmDataImpl {
    private static final String DATABASE_NAME = "DB_DRINK_ALARM";

    /*Version database*/
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DRINK_ALARM = "TABLE_DRINK_ALARM";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_START_TIME = "starttime";
    public static final String COLUMN_END_TIME = "endtime";
    public static final String COLUMN_PERIOD = "period";
    public static final String COLUMN_STATUS = "status";
    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public AlarmData(Context c){
        AlarmData.context = c;
    }

    public AlarmData open() throws SQLException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }



    public Cursor SETUPCURSOR(){
        String[] columns = new String[] {COLUMN_ID, COLUMN_USERNAME, COLUMN_START_TIME, COLUMN_END_TIME, COLUMN_PERIOD, COLUMN_STATUS};
        Cursor c = db.query(TABLE_DRINK_ALARM, columns, null, null, null, null, null, null);
        return c;
    }

    @Override
    public long INSERT(String username, Alarm alarm) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_START_TIME, alarm.getStartTime());
        cv.put(COLUMN_END_TIME, alarm.getEndTime());
        cv.put(COLUMN_PERIOD, alarm.getPeriod());
        cv.put(COLUMN_STATUS, alarm.getStatus());
        return db.insert(TABLE_DRINK_ALARM, null, cv);
    }

    @Override
    public boolean CHECKEXISTED(String user) {
        Cursor c = SETUPCURSOR();
        boolean check = false;
        int iusername = c.getColumnIndex(COLUMN_USERNAME);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (user.equals(c.getString(iusername))){
                check = true;
                break;
            }
        }
        c.close();
        return check;
    }

    @Override
    public Alarm GETALARM(String user) {
        Alarm alarm = null;
        Cursor c = SETUPCURSOR();
        int iusername = c.getColumnIndex(COLUMN_USERNAME);
        int istartTime = c.getColumnIndex(COLUMN_START_TIME);
        int iendTime = c.getColumnIndex(COLUMN_END_TIME);
        int iperiod = c.getColumnIndex(COLUMN_PERIOD);
        int istatus = c.getColumnIndex(COLUMN_STATUS);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (user.equals(c.getString(iusername))){
                alarm = new Alarm(c.getString(istartTime), c.getString(iendTime),
                        c.getString(iperiod), c.getString(istatus));
                break;
            }
        }
        c.close();
        return alarm;
    }

    @Override
    public long UPDATEALARM(String username, Alarm alarm) {
        Cursor c = SETUPCURSOR();

        boolean check = false;
        String id = null;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_START_TIME, alarm.getStartTime());
        cv.put(COLUMN_END_TIME, alarm.getEndTime());
        cv.put(COLUMN_PERIOD, alarm.getPeriod());
        cv.put(COLUMN_STATUS, alarm.getStatus());

        int irow = c.getColumnIndex(COLUMN_ID);
        int iusername = c.getColumnIndex(COLUMN_USERNAME);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (username.equals(c.getString(iusername))){
                check = true;
                id = c.getString(irow);
                break;
            }
        }

        c.close();

        if (check){
            return db.update(TABLE_DRINK_ALARM, cv, COLUMN_ID + "=" + id, null);
        } else return 0;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            arg0.execSQL("CREATE TABLE " + TABLE_DRINK_ALARM + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT NOT NULL, "
                    + COLUMN_START_TIME + " TEXT NOT NULL, "
                    + COLUMN_END_TIME + " TEXT NOT NULL, "
                    + COLUMN_PERIOD + " TEXT NOT NULL, "
                    + COLUMN_STATUS + " TEXT NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINK_ALARM);
            onCreate(arg0);
        }
    }
}
