package com.example.jason.jason_workshop_3.Model.UserModel.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserSetUp;
import com.example.jason.jason_workshop_3.Model.UserModel.Interface.UserSetUpDatabaseImpl;

/**
 * Created by jason on 21/06/2016.
 */
public class UserSetUpDatabase implements UserSetUpDatabaseImpl{
    private static final String DATABASE_NAME = "DB_USER_SETUP";

    /*Version database*/
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER_SETUP = "TABLE_USER_SETUP";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_BMI_CHECK_TYPE = "bmi_check_type";
    public static final String COLUMN_DAILY_DRINK_ALARM = "daily_drink_alarm";
    public static final String COLUMN_DAILY_FITNESS_ALARM = "daily_fitness_alarm";
    public static final String COLUMN_DAILY_DIET_ALARM = "daily_diet_alarm";

    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public UserSetUpDatabase (Context c){
        UserSetUpDatabase.context = c;
    }

    public UserSetUpDatabase open() throws SQLException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }

    @Override
    public long INSERT(UserSetUp userSetUp) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, userSetUp.getUSERNAME());
        cv.put(COLUMN_BMI_CHECK_TYPE, userSetUp.getUSER_CHECK_BMI_TYPE());
        cv.put(COLUMN_DAILY_DRINK_ALARM, userSetUp.getDAILY_DIET_ALARM());
        cv.put(COLUMN_DAILY_FITNESS_ALARM, userSetUp.getDAILY_FITNESS_ALARM());
        cv.put(COLUMN_DAILY_DIET_ALARM, userSetUp.getDAILY_DIET_ALARM());
        return db.insert(TABLE_USER_SETUP, null, cv);
    }

    @Override
    public long UPDATE_BMI_CHECK_TYPE(String ID, String checktype) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAILY_DRINK_ALARM, checktype);
        return db.update(TABLE_USER_SETUP, cv, COLUMN_ID + "=" + ID, null);
    }

    @Override
    public long UPDATE_DAILY_DRINK_ALARM(String ID, String mBoolean) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAILY_DRINK_ALARM, mBoolean);
        return db.update(TABLE_USER_SETUP, cv, COLUMN_ID + "=" + ID, null);
    }

    @Override
    public long UPDATE_DAILY_FITNESS_ALARM(String ID, String mBoolean) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAILY_DRINK_ALARM, mBoolean);
        return db.update(TABLE_USER_SETUP, cv, COLUMN_ID + "=" + ID, null);
    }

    @Override
    public long UPDATE_DAILY_DIET_ALARM(String ID, String mBoolean) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAILY_DRINK_ALARM, mBoolean);
        return db.update(TABLE_USER_SETUP, cv, COLUMN_ID + "=" + ID, null);
    }


    @Override
    public UserSetUp CHECK_SETUP(String Username) {
        Cursor c = SETUPCURSOR();
        UserSetUp userSetUp = new UserSetUp("","","","","");
        int iuserName = c.getColumnIndex(COLUMN_USERNAME);
        int ichecktype = c.getColumnIndex(COLUMN_BMI_CHECK_TYPE);
        int idailyDrink = c.getColumnIndex(COLUMN_DAILY_DRINK_ALARM);
        int idailyFitness = c.getColumnIndex(COLUMN_DAILY_FITNESS_ALARM);
        int idailyDiet = c.getColumnIndex(COLUMN_DAILY_DIET_ALARM);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (Username.equals(c.getString(iuserName))){
                userSetUp = new UserSetUp(Username, c.getString(ichecktype), c.getString(idailyDrink),
                        c.getString(idailyFitness), c.getString(idailyDiet));
                break;
            }
        }
        return userSetUp;
    }

    @Override
    public Cursor SETUPCURSOR() {
        String[] columns = new String[] {COLUMN_ID, COLUMN_USERNAME, COLUMN_BMI_CHECK_TYPE,
                COLUMN_DAILY_DRINK_ALARM,  COLUMN_DAILY_FITNESS_ALARM, COLUMN_DAILY_DIET_ALARM};
        Cursor c = db.query(TABLE_USER_SETUP, columns, null, null, null, null, null, null);
        return c;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            arg0.execSQL("CREATE TABLE " + TABLE_USER_SETUP + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT NOT NULL, "
                    + COLUMN_BMI_CHECK_TYPE + " TEXT NOT NULL, "
                    + COLUMN_DAILY_DRINK_ALARM + " TEXT NOT NULL, "
                    + COLUMN_DAILY_FITNESS_ALARM + " TEXT NOT NULL, "
                    + COLUMN_DAILY_DIET_ALARM + " TEXT NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SETUP);
            onCreate(arg0);
        }
    }
}
