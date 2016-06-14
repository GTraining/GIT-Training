package com.example.jason.jason_workshop_3.Presenter.PresentLogin;

import com.example.jason.jason_workshop_3.Model.UserData.User;

/**
 * Created by jason on 13/06/2016.
 */
public interface LoginImpl {
    boolean checkUser(User user);
    void onClickCheckUser();
}
