package com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;

/**
 * Created by jason on 13/06/2016.
 */
public interface PUserManagementImpl {
    void createUser(User user);
    void UpdateHealth(String id, String health);
    void UpdateLoginStatus(String id, String st);
    User getUser(String username);
    UserCheckInfo checkExisting(String username);
    CurrentLogin checkCurrentLogin();
    void closeDatabase();
}
