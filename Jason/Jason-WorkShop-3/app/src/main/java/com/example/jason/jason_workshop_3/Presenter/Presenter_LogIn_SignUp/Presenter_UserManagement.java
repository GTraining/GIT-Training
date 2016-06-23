package com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp;

import android.content.Context;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserDatabase;

/**
 * Created by jason on 13/06/2016.
 */
public class Presenter_UserManagement implements UserManagementImpl {
    Context mContext;
    UserDatabase mUserDatabase;

    public Presenter_UserManagement(Context mContext) {
        this.mContext = mContext;
        mUserDatabase = new UserDatabase(mContext);
        mUserDatabase.open();
    }

    @Override
    public void createUser(User user) {
        mUserDatabase.InsertUSER(user.getUserName(), user.getPassword(), "new_user", "on");
    }

    @Override
    public void UpdateLoginStatus(String id, String login) {
        mUserDatabase.UpdateLoginStatus(id, login);
    }

    @Override
    public void UpdateHealth(String id, String st) {
        mUserDatabase.UpdateHealthStatus(id, st);
    }

    @Override
    public User getUser(String username) {
        return mUserDatabase.getUser(username);
    }

    @Override
    public UserCheckInfo checkExisting(String username) {
        return mUserDatabase.checkUser(username);
    }

    @Override
    public CurrentLogin checkCurrentLogin() {
        return mUserDatabase.CheckCurrentLogin();
    }

    @Override
    public void closeDatabase() {
        mUserDatabase.close();
    }

}
