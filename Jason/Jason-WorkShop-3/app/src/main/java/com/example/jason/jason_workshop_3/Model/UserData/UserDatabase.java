package com.example.jason.jason_workshop_3.Model.UserData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_LOGIN = "login";
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


    public long InsertUSER(String us, String pw, String st, String login){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, us);
        cv.put(COLUMN_PASSWORD, pw);
        cv.put(COLUMN_STATUS, st);
        cv.put(COLUMN_LOGIN, login);
        return db.insert(TABLE_USER_LOGIN, null, cv);
    }
    public long UpdateStatus(String id, String st){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, st);
        return db.update(TABLE_USER_LOGIN, cv, COLUMN_ID + " = " + id, null);
    }
    public long UpdateSLogin(String id, String login){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGIN, login);
        return db.update(TABLE_USER_LOGIN, cv, COLUMN_ID + " = " + id, null);
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
    public CheckLogin checkUser(String username) {
        Cursor c = SetupCursor();
        CheckLogin check = new CheckLogin("","","","","",false);
        int iRow = c.getColumnIndex(COLUMN_ID);
        int iUsername = c.getColumnIndex(COLUMN_USERNAME);
        int iPassword = c.getColumnIndex(COLUMN_PASSWORD);
        int iStatus = c.getColumnIndex(COLUMN_STATUS);
        int iLogin = c.getColumnIndex(COLUMN_LOGIN);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String user = c.getString(iUsername);
            if (user.equals(username)) {
                check = new CheckLogin(c.getString(iRow),
                        user, c.getString(iPassword),
                        c.getString(iStatus),
                        c.getString(iLogin), true);
                break;
            }
        }
        c.close();
        return check;
    }

    public Cursor SetupCursor(){
        String[] columns = new String[] {COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_STATUS, COLUMN_LOGIN};
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
                    + COLUMN_STATUS + " TEXT NOT NULL, "
                    + COLUMN_LOGIN + " TEXT NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LOGIN);
            onCreate(arg0);
        }
    }
}
