package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

/**
 * Created by jason on 16/06/2016.
 */
public class UserCheckCurrentLogin {
    public String ID;
    public String USERNAME;

    public UserCheckCurrentLogin(String ID, String USERNAME) {
        this.ID = ID;
        this.USERNAME = USERNAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }
}
