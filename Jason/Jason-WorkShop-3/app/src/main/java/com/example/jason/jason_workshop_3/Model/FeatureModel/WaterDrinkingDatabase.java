package com.example.jason.jason_workshop_3.Model.FeatureModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 16/06/2016.
 */
public class WaterDrinkingDatabase implements WaterDrinkingDatabaseImpl {

    private static final String DATABASE_NAME = "DB_MANAGE_DRINK_WATER";

    /*Version database*/
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DRINK_WATER = "TABLE_DRINK_WATER";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_AMOUNT_CUP_WATER = "amount_cup_water";
    public static final String COLUMN_DATE = "date";
    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public WaterDrinkingDatabase(Context c){
        WaterDrinkingDatabase.context = c;
    }

    public WaterDrinkingDatabase open() throws SQLException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }



    @Override
    public long INSERT(WaterCup waterCup) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, waterCup.getUSERNAME());
        cv.put(COLUMN_AMOUNT_CUP_WATER, waterCup.getAMOUNTOFCUP());
        cv.put(COLUMN_DATE, waterCup.getDATE());
        return db.insert(TABLE_DRINK_WATER, null, cv);
    }



    @Override
    public long UPDATE(WaterCup waterCup) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AMOUNT_CUP_WATER, waterCup.getAMOUNTOFCUP());
        String id = "0";
        boolean check = false;
        Cursor c = SETUPCURSOR();
        int row = c.getColumnIndex(COLUMN_ID);
        int Username = c.getColumnIndex(COLUMN_USERNAME);
        int date = c.getColumnIndex(COLUMN_DATE);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (waterCup.getUSERNAME().equals(c.getString(Username)) && waterCup.getDATE().equals(c.getString(date))){
                id = c.getString(row);
                check = true;
                break;
            }
        }
        if (check){
            c.close();
            return db.update(TABLE_DRINK_WATER, cv, COLUMN_ID + "=" + id, null);
        }
        else {
            c.close();
            return 0;
        }

    }

    @Override
    public List<WaterCup> GETLIST(String Username) {
        Cursor c = SETUPCURSOR();
        int _username = c.getColumnIndex(COLUMN_USERNAME);
        int _amount = c.getColumnIndex(COLUMN_AMOUNT_CUP_WATER);
        int _date = c.getColumnIndex(COLUMN_DATE);
        List<WaterCup> waterCups = new ArrayList<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (Username.equals(c.getString(_username))){
                waterCups.add(new WaterCup(Username, c.getString(_amount), c.getString(_date)));
            }
        }
        c.close();
        return waterCups;
    }

    @Override
    public WaterCup GETOWN(String username, String date) {
        Cursor c = SETUPCURSOR();
        int _username = c.getColumnIndex(COLUMN_USERNAME);
        int _amount = c.getColumnIndex(COLUMN_AMOUNT_CUP_WATER);
        int _date = c.getColumnIndex(COLUMN_DATE);
        WaterCup mWaterCup = new WaterCup("", "", "");
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (username.equals(c.getString(_username)) && date.equals(c.getString(_date))){
                mWaterCup = new WaterCup(c.getString(_username), c.getString(_amount), c.getString(_date));
            }
        }
        c.close();
        return mWaterCup;
    }

    @Override
    public boolean CHECKEXISTED(String username, String date) {
        Cursor c = SETUPCURSOR();
        boolean check = false;
        int _username = c.getColumnIndex(COLUMN_USERNAME);
        int _date = c.getColumnIndex(COLUMN_DATE);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (username.equals(c.getString(_username)) && date.equals(c.getString(_date))){
                check = true;
                break;
            }
        }
        c.close();
        return check;
    }


    public Cursor SETUPCURSOR(){
        String[] columns = new String[] {COLUMN_ID, COLUMN_USERNAME, COLUMN_AMOUNT_CUP_WATER, COLUMN_DATE};
        Cursor c = db.query(TABLE_DRINK_WATER, columns, null, null, null, null, null, null);
        return c;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            arg0.execSQL("CREATE TABLE " + TABLE_DRINK_WATER + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT NOT NULL, "
                    + COLUMN_AMOUNT_CUP_WATER + " TEXT NOT NULL, "
                    + COLUMN_DATE + " TEXT NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINK_WATER);
            onCreate(arg0);
        }
    }
}
