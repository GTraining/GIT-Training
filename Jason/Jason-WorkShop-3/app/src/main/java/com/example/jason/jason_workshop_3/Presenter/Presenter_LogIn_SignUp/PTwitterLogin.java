package com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp;

import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.View.LoginView.TwitterLoginActivity;

/**
 * Created by jason on 27/06/2016.
 */
public class PTwitterLogin {
    private TwitterLoginActivity mView;
    private PUserManagement mUserManagement;
    private String username;
    private UserCheckInfo mCheckInfo;

    public PTwitterLogin(TwitterLoginActivity mView, String username) {
        this.mView = mView;
        this.username = username;
        mUserManagement = new PUserManagement(mView);
    }

    public void checkTwitterUser(){
        mCheckInfo = mUserManagement.checkExisting("@" + username);

        if (!mCheckInfo.isExisted()){
            mUserManagement.createUser(new User("@" + username, ""));
            startMain();
        } else startMain();
    }

    public void startMain(){
        mCheckInfo = mUserManagement.checkExisting("@" + username);
        mUserManagement.UpdateLoginStatus(mCheckInfo.getID(), "on");
        if (mCheckInfo.getStatus().equals("new_user")) {
            mUserManagement.UpdateHealth(mCheckInfo.getID(), "Older_User");
            mView.startFirstCheckBMI();
        }
        else{
            mView.startUserMain();
        }
        mUserManagement.closeDatabase();
    }
    public void logoutTwitter(){

        mCheckInfo = mUserManagement.checkExisting("@" + username);
        mUserManagement.UpdateLoginStatus(mCheckInfo.getID(), "off");
        mUserManagement.closeDatabase();
    }

}
