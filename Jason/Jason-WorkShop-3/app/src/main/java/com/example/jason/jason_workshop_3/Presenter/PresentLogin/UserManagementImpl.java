package com.example.jason.jason_workshop_3.Presenter.PresentLogin;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;

/**
 * Created by jason on 13/06/2016.
 */
public interface UserManagementImpl {
    void createUser(User user);
    void UpdateBMI(String id, String login);
    void UpdateLoginStatus(String id, String st);
    User getUser(String username);
    UserCheckInfo checkExisting(String username);
    UserCheckCurrentLogin checkCurrentLogin();
    void closeDatabase();
    void UpdateAllLoginStatus();
}
