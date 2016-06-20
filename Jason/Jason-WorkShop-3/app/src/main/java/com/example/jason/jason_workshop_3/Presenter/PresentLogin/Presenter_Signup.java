package com.example.jason.jason_workshop_3.Presenter.PresentLogin;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.View.LoginView.Signup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 20/06/2016.
 */
public class Presenter_Signup implements SignupImpl{

    private String Username, password, cf_password;
    private Signup mView;
    private UserManagement mUserMangement;
    private List<String> list = new ArrayList<>();
    public Presenter_Signup(Signup mView) {
        this.mView = mView;
        mUserMangement = new UserManagement(mView);

    }

    @Override
    public void SignUp() {
        list = mView.getSignUpInfor();
        Username = list.get(0);
        password = list.get(1);
        cf_password = list.get(2);
        if (Empty(Username, password, cf_password)) {
            Toast.makeText(mView, "Something is empty!", Toast.LENGTH_LONG).show();

        }
        else if (!checkPassword(password, cf_password)){
            Toast.makeText(mView, "Confirm password is incorrect!", Toast.LENGTH_LONG).show();
            mView.UpdatePasswordEditText();
        }
        else {
            UserCheckInfo mCheckLogin = mUserMangement.checkExisting(Username);
            if (!mCheckLogin.isExisted()){
                mUserMangement.createUser(new User(Username, password));
                mView.OpenNewUserActivity();
            } else {
                Toast.makeText(mView, "Username is existed!", Toast.LENGTH_LONG).show();
                mView.UpdateAllEditText();
            }
        }
    }

    @Override
    public boolean Empty(String us, String pw, String cf_pw) {
        boolean check = false;
        if (us.equals("") || pw.equals("") || cf_pw.equals(""))
            check = true;
        return check;
    }

    @Override
    public boolean checkPassword(String pw, String cf_pw) {
        if (pw.equals(cf_pw)){
            return true;
        }else return false;
    }
}
