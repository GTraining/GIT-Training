package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

/**
 * Created by jason on 14/06/2016.
 */
public class UserCheckInfo {
    public String ID;
    public String UserName;
    public String Password;
    public String Status;
    public String Login;
    public boolean Existed;

    public UserCheckInfo(String ID, String userName, String password, String status, String login, boolean existed) {
        this.ID = ID;
        UserName = userName;
        Password = password;
        Login = login;
        Status = status;
        this.Existed = existed;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isExisted() {
        return Existed;
    }

    public void setExisted(boolean existed) {
        Existed = existed;
    }
}
