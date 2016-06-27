package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PMonthlyBMI;

import android.content.Context;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;

/**
 * Created by jason on 20/06/2016.
 */
public class PMonthlyCheckBMI {
    private Context mContext;
    private PUserManagement userManagement;

    public PMonthlyCheckBMI(Context mContexts) {
        this.mContext = mContexts;
        this.userManagement = new PUserManagement(mContext);
    }

    public String setCheckTitle(){
        String username = "";
        CurrentLogin mCurrentLogin = userManagement.checkCurrentLogin();
        username = mCurrentLogin.getUSERNAME();
        userManagement.closeDatabase();
        return "Hi! " + username + ".";
    }
}
