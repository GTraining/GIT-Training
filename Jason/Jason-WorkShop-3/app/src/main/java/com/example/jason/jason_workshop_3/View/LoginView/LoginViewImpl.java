package com.example.jason.jason_workshop_3.View.LoginView;

import android.view.View;

/**
 * Created by jason on 13/06/2016.
 */
public interface LoginViewImpl {
    String getUserName();
    String getPassword();
    void onClickCheckUser(View view);
    void onClickUserRegister(View view);
    void onclickSignUp(View v);
    void onclickCloseDialog(View v);
    void OpenMainActivity();
    void OpenNewUserActivity();
}