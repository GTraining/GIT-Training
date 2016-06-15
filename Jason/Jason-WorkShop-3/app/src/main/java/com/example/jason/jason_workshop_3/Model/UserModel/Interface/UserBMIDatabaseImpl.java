package com.example.jason.jason_workshop_3.Model.UserModel.Interface;

import android.database.Cursor;


import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;

import java.util.List;

/**
 * Created by jason on 15/06/2016.
 */
public interface UserBMIDatabaseImpl {
    long INSERT(UserBMI userBMI);
    long UPDATE(UserBMI userBMI);
    List<UserBMI> GETLIST(String Username);
    Cursor SETUPCURSOR();
}
