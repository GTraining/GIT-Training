package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import android.content.Context;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.Presenter_UserManagement;

/**
 * Created by jason on 20/06/2016.
 */
public class Presenter_MonthlyCheckBMI {
    private Context mContext;
    private Presenter_UserManagement userManagement;

    public Presenter_MonthlyCheckBMI(Context mContexts) {
        this.mContext = mContexts;
        this.userManagement = new Presenter_UserManagement(mContext);
    }

    public String setCheckTitle(){
        String username = "";
        CurrentLogin mCurrentLogin = userManagement.checkCurrentLogin();
        username = mCurrentLogin.getUSERNAME();
        userManagement.closeDatabase();
        return "Hi! " + username + ".";
    }
}
