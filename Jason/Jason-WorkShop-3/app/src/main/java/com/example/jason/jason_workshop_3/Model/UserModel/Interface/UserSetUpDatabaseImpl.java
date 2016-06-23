package com.example.jason.jason_workshop_3.Model.UserModel.Interface;

import android.database.Cursor;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserSetUp;

import java.util.List;

/**
 * Created by jason on 21/06/2016.
 */
public interface UserSetUpDatabaseImpl {
    long INSERT(UserSetUp userSetUp);
    long UPDATE_BMI_CHECK_TYPE(String ID, String checktype);
    long UPDATE_DAILY_DRINK_ALARM(String ID, String mBoolean);
    long UPDATE_DAILY_FITNESS_ALARM(String ID, String mBoolean);
    long UPDATE_DAILY_DIET_ALARM(String ID, String mBoolean);
    UserSetUp CHECK_SETUP(String username);
    Cursor SETUPCURSOR();
}
