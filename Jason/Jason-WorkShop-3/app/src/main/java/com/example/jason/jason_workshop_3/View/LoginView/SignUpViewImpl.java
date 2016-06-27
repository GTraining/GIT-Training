package com.example.jason.jason_workshop_3.View.LoginView;

import android.view.View;
import java.util.List;

/**
 * Created by jason on 20/06/2016.
 */
public interface SignUpViewImpl {
    List<String> getSignUpInfor();
    void onclickBack(View v);
    void onclickSignUp(View v);
    void UpdateAllEditText();
    void UpdatePasswordEditText();
    void OpenNewUserActivity();
    void startActitivities(Class mClass, String intent);
}
