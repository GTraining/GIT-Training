package com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp;

/**
 * Created by jason on 20/06/2016.
 */
public interface SignupImpl {
    void SignUp();
    boolean Empty(String us, String pw, String cf_pw);
    boolean checkPassword(String pw, String cf_pw);
}
