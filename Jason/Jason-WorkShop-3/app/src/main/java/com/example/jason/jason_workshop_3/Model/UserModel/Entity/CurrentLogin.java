package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

/**
 * Created by jason on 16/06/2016.
 */
public class CurrentLogin {
    public boolean CHECK;
    public String ID;
    public String USERNAME;

    public CurrentLogin(boolean CHECK, String ID, String USERNAME) {
        this.CHECK = CHECK;
        this.ID = ID;
        this.USERNAME = USERNAME;
    }

    public boolean isCHECK() {
        return CHECK;
    }

    public void setCHECK(boolean CHECK) {
        this.CHECK = CHECK;
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
