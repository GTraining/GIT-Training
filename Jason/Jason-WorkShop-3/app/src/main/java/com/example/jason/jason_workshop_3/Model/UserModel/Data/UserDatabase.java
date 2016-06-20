package com.example.jason.jason_workshop_3.Model.UserModel.Data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;

/**
 * Created by jason on 13/06/2016.
 */
public class UserDatabase {
    private static final String DATABASE_NAME = "DB_USER_LOGIN";

    /*Version database*/
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER_LOGIN = "TABLE_USER_LOGIN";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_BMI = "bmi";
    public static final String COLUMN_LOGIN_STATUS = "login_status";
    private static Context context;
    static SQLiteDatabase db;
    private OpenHelper openHelper;

    public UserDatabase (Context c){
        UserDatabase.context = c;
    }

    public UserDatabase open() throws SQLException {
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }


    public long InsertUSER(String us, String pw, String bmi, String login){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, us);
        cv.put(COLUMN_PASSWORD, pw);
        cv.put(COLUMN_BMI, bmi);
        cv.put(COLUMN_LOGIN_STATUS, login);
        return db.insert(TABLE_USER_LOGIN, null, cv);
    }

    public long UpdateBMI(String id, String bmi){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BMI, bmi);
        return db.update(TABLE_USER_LOGIN, cv, COLUMN_ID + "=" + id, null);
    }
    public long UpdateLoginStatus(String id, String login){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGIN_STATUS, login);
        return db.update(TABLE_USER_LOGIN, cv, COLUMN_ID + "=" + id, null);

    }
    public long UpdateAllLoginStatus(){
        String login_status = "off";
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGIN_STATUS, login_status);
        Cursor c = SetupCursor();
        int irow = c.getColumnIndex(COLUMN_ID);
        int login_stt = c.getColumnIndex(COLUMN_LOGIN_STATUS);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(login_stt).equals("on")){
                return db.update(TABLE_USER_LOGIN, cv, COLUMN_ID + "=" + c.getString(irow), null);
            }
        }
        return 0;
    }
    public User getUser(String username) {
        Cursor c = SetupCursor();
        User mUser = new User("none", "none");
        int iUsername = c.getColumnIndex(COLUMN_USERNAME);
        int iPassword = c.getColumnIndex(COLUMN_PASSWORD);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String user = c.getString(iUsername);
            String password = c.getString(iPassword);
            if (user.equals(username)) {
                mUser = new User(user, password);
                break;
            }
        }
        c.close();
        return mUser;
    }
    public UserCheckInfo checkUser(String username) {
        Cursor c = SetupCursor();
        UserCheckInfo check = new UserCheckInfo("","","","","",false);
        int iRow = c.getColumnIndex(COLUMN_ID);
        int iUsername = c.getColumnIndex(COLUMN_USERNAME);
        int iPassword = c.getColumnIndex(COLUMN_PASSWORD);
        int iStatus = c.getColumnIndex(COLUMN_BMI);
        int iLogin = c.getColumnIndex(COLUMN_LOGIN_STATUS);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String user = c.getString(iUsername);
            if (user.equals(username)) {
                check = new UserCheckInfo(c.getString(iRow),
                        user, c.getString(iPassword),
                        c.getString(iStatus),
                        c.getString(iLogin), true);
                break;
            }
        }
        c.close();
        return check;
    }
    public UserCheckCurrentLogin CheckCurrentLogin(){
        Cursor c = SetupCursor();
        UserCheckCurrentLogin mCheck = new UserCheckCurrentLogin("", "");
        int iUsername = c.getColumnIndex(COLUMN_USERNAME);
        int irow = c.getColumnIndex(COLUMN_ID);
        int iLogin = c.getColumnIndex(COLUMN_LOGIN_STATUS);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String login = c.getString(iLogin);
            if (login.equals("on")) {
                mCheck = new UserCheckCurrentLogin(c.getString(irow), c.getString(iUsername));
                break;
            }
        }
        c.close();
        return mCheck;

    }
    public Cursor SetupCursor(){
        String[] columns = new String[] {COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_BMI, COLUMN_LOGIN_STATUS};
        Cursor c = db.query(TABLE_USER_LOGIN, columns, null, null, null, null, null, null);
        return c;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            arg0.execSQL("CREATE TABLE " + TABLE_USER_LOGIN + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT NOT NULL, "
                    + COLUMN_PASSWORD + " TEXT NOT NULL, "
                    + COLUMN_BMI + " TEXT NOT NULL, "
                    + COLUMN_LOGIN_STATUS + " TEXT NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LOGIN);
            onCreate(arg0);
        }
    }
}
