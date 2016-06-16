package com.example.jason.jason_workshop_3.Presenter.PresentLogin;

import android.content.Context;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Interface.UserManagementImpl;

/**
 * Created by jason on 13/06/2016.
 */
public class UserMangement implements UserManagementImpl {
    Context mContext;
    UserDatabase mUserDatabase;

    public UserMangement(Context mContext) {
        this.mContext = mContext;
        mUserDatabase = new UserDatabase(mContext);
        mUserDatabase.open();
    }

    @Override
    public void createUser(User user) {
        mUserDatabase.InsertUSER(user.getUserName(), user.getPassword(), "new", "off");
    }

    @Override
    public void UpdateLogin(String id, String login) {
        mUserDatabase.UpdateSLogin(id, login);
    }
    @Override
    public void UpdateStatus(String id, String st) {
        mUserDatabase.UpdateStatus(id, st);
    }

    @Override
    public User getUser(String username) {
        return mUserDatabase.getUser(username);
    }

    @Override
    public UserCheckInfo checkExisting(String username) {
        return mUserDatabase.checkUser(username);
    }
}