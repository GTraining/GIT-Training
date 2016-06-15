package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

import com.example.jason.jason_workshop_3.Model.UserModel.Interface.UserImpl;

/**
 * Created by jason on 13/06/2016.
 */
public class User implements UserImpl {
    public String UserName;
    public String password;

    public User(String userName, String password) {
        UserName = userName;
        this.password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkUsername(String username) {
        if (username.equals(getUserName())) return true;
        else return false;
    }

    @Override
    public boolean checkPassword(String password) {
        if (password.equals(getPassword())) return true;
        else return false;
    }
}
