package com.example.jason.jason_workshop_3.Model.UserModel.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Interface.UserBMIDatabaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 15/06/2016.
 */
public class UserBMIDatabase implements UserBMIDatabaseImpl {
    private static final String DATABASE_NAME = "DB_USER_HEALTH";

    /*Version database*/
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER_HEALTH = "TABLE_USER_HEALTH";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_CHECKDATE = "checktime";
    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public UserBMIDatabase(Context c){
        UserBMIDatabase.context = c;
    }

    public UserBMIDatabase open() throws SQLException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }



    @Override
    public long INSERT(UserBMI userBMI) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, userBMI.getUSERNAME());
        cv.put(COLUMN_AGE, userBMI.getAGE());
        cv.put(COLUMN_HEIGHT, userBMI.getHEIGHT());
        cv.put(COLUMN_WEIGHT, userBMI.getWEIGHT());
        cv.put(COLUMN_CHECKDATE, userBMI.getCHECKTIME());
        return db.insert(TABLE_USER_HEALTH, null, cv);
    }

    @Override
    public long UPDATE(UserBMI userBMI) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_HEIGHT, userBMI.getHEIGHT());
        cv.put(COLUMN_WEIGHT, userBMI.getWEIGHT());
        String id = "0";
        boolean check = false;
        Cursor c = SETUPCURSOR();
        int row = c.getColumnIndex(COLUMN_ID);
        int Username = c.getColumnIndex(COLUMN_USERNAME);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (userBMI.getUSERNAME().equals(c.getString(Username))){
                id = c.getString(row);
                check = true;
                break;
            }
        }
        if (check){
            return db.update(TABLE_USER_HEALTH, cv, COLUMN_ID + "=" + id, null);
        } else {
            return 0;
        }
    }

    @Override
    public List<UserBMI> GETLIST(String Username) {
        Cursor c = SETUPCURSOR();
        int iusername = c.getColumnIndex(COLUMN_USERNAME);
        int iage = c.getColumnIndex(COLUMN_AGE);
        int iheight = c.getColumnIndex(COLUMN_HEIGHT);
        int iweight = c.getColumnIndex(COLUMN_WEIGHT);
        int icheckDate = c.getColumnIndex(COLUMN_CHECKDATE);
        List<UserBMI> userBMIs = new ArrayList<>();
        userBMIs.add(new UserBMI(Username, "23", "174", "51",  "1/1/2016"));
        userBMIs.add(new UserBMI(Username, "23", "174", "52",  "1/2/2016"));
        userBMIs.add(new UserBMI(Username, "23", "174", "53",  "1/3/2016"));
        userBMIs.add(new UserBMI(Username, "23", "174", "53",  "1/4/2016"));
        userBMIs.add(new UserBMI(Username, "23", "174", "54",  "1/5/2016"));
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (Username.equals(c.getString(iusername))){
                userBMIs.add(new UserBMI(Username, c.getString(iage),
                        c.getString(iheight), c.getString(iweight),
                        c.getString(icheckDate)));
            }
        }
        c.close();
        return userBMIs;
    }

    public Cursor SETUPCURSOR(){
        String[] columns = new String[] {COLUMN_ID, COLUMN_USERNAME, COLUMN_AGE, COLUMN_HEIGHT, COLUMN_WEIGHT, COLUMN_CHECKDATE};
        Cursor c = db.query(TABLE_USER_HEALTH, columns, null, null, null, null, null, null);
        return c;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            arg0.execSQL("CREATE TABLE " + TABLE_USER_HEALTH + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT NOT NULL, "
                    + COLUMN_AGE + " TEXT NOT NULL, "
                    + COLUMN_HEIGHT + " TEXT NOT NULL, "
                    + COLUMN_WEIGHT + " TEXT NOT NULL, "
                    + COLUMN_CHECKDATE + " TEXT NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HEALTH);
            onCreate(arg0);
        }
    }
}
