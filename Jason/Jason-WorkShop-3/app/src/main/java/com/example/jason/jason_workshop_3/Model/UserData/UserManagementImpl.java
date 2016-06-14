package com.example.jason.jason_workshop_3.Model.UserData;

/**
 * Created by jason on 13/06/2016.
 */
public interface UserManagementImpl {
    void createUser(User user);
    void UpdateStatus(String id, String login);
    void UpdateLogin(String id, String st);
    User getUser(String username);
    CheckLogin checkExisting(String username);
}
