package com.example.jason.jason_workshop_3.Presenter.PresentLogin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserSetUpDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserSetUp;
import com.example.jason.jason_workshop_3.View.LoginView.SignupActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 20/06/2016.
 */
public class Presenter_Signup implements SignupImpl{

    private String Username, password, cf_password;
    private SignupActivity mView;
    private Presenter_UserManagement mUserManagement;
    private UserSetUpDatabase mUserSetUpDatabase;
    private List<String> list = new ArrayList<>();
    private UserSetUp mUserSetUp;
    public Presenter_Signup(SignupActivity mView) {
        this.mView = mView;
        mUserManagement = new Presenter_UserManagement(mView);
        mUserSetUpDatabase = new UserSetUpDatabase(mView);
        mUserSetUpDatabase.open();
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
            UserCheckInfo mCheckLogin = mUserManagement.checkExisting(Username);
            if (!mCheckLogin.isExisted()){
                mUserSetUp = new UserSetUp(Username, "MONTHLY", "1", "1","1");
                new LoadNewUser().execute();
                // Fix Bug ID: JS_010 - Application will be show up Home screen after user sign up successful
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

    public class LoadNewUser extends AsyncTask<Void, Void, Void>{
        ProgressDialog mDialog = new ProgressDialog(mView);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setProgress(0);
            mDialog.setMessage("Setting up......");
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            mUserManagement.createUser(new User(Username, password));
            mUserSetUpDatabase.INSERT(mUserSetUp);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mView.OpenNewUserActivity();
            mDialog.dismiss();
        }
    }
}
