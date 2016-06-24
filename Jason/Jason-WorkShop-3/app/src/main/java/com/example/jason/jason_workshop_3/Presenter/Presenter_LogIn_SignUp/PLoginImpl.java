package com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;

/**
 * Created by jason on 13/06/2016.
 */
public interface PLoginImpl {
    boolean checkUser(User user);
    void onClickCheckUser();
}
