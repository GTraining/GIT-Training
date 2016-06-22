package com.example.jason.jason_workshop_3.Presenter.PresentMain;

import android.content.Context;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.UserManagement;

/**
 * Created by jason on 20/06/2016.
 */
public class Presenter_CheckBMI {
    private Context mContext;
    private UserManagement userManagement;

    public Presenter_CheckBMI(Context mContexts) {
        this.mContext = mContexts;
        this.userManagement = new UserManagement(mContext);
    }

    public String getCurrenUser(){
        String username = "";
        UserCheckCurrentLogin mCurrentLogin = userManagement.checkCurrentLogin();
        username = mCurrentLogin.getUSERNAME();
        return username;
    }
}
