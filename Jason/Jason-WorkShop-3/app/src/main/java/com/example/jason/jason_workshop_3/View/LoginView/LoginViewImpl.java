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
    void OpenMainActivity();
    void OpenNewUserActivity();
    void resetEditText();
    void startActivities(Class mClass, String intent);
}
