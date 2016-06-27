package com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.View.LoginView.LoginActivity;

/**
 * Created by jason on 13/06/2016.
 */
public class PLogin implements PLoginImpl {

    private User mUser;
    private PUserManagement mUserManagement;
    private LoginActivity mView;
    private UserCheckInfo mCheckLogin;

    public PLogin(LoginActivity mViews) {
        this.mView = mViews;
        mUserManagement = new PUserManagement(mView);
    }

    @Override
    public boolean checkUser(User user) {
        boolean check = false;
        if (user.checkUsername(mView.getUserName())){
            if (user.checkPassword(mView.getPassword())){
                check = true;
            }
            else check = false;
        }
        return check;
    }

    @Override
    public void onClickCheckUser() {
        if (Empty()) Toast.makeText(mView, "Something is empty!", Toast.LENGTH_LONG).show();
        else {
            mUser = mUserManagement.getUser(mView.getUserName());
            new CheckUsers(mUser).execute();
        }
    }

    private class CheckUsers extends AsyncTask<Void, Void, Boolean> {
        User user;

        public CheckUsers(User users) {
            this.user = users;
        }

        ProgressDialog mProgressDialog = new ProgressDialog(mView);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Checking...");
            mProgressDialog.setProgress(0);
            mProgressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return checkUser(user);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                mCheckLogin = mUserManagement.checkExisting(mUser.getUserName());
                mUserManagement.UpdateLoginStatus(mCheckLogin.getID(), "on");
                if (mCheckLogin.getStatus().equals("new_user")) {
                    mUserManagement.UpdateHealth(mCheckLogin.getID(), "Older_User");
                    mUserManagement.closeDatabase();
                    mView.OpenNewUserActivity();
                }
                else{
                    mUserManagement.closeDatabase();
                    mView.OpenMainActivity();
                }
                mProgressDialog.dismiss();
            }else {
                Toast.makeText(mView, "Username or Password is incorrect!\n Please try again.", Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
            super.onPostExecute(aBoolean);
        }
    }

    public boolean Empty(){
        if (mView.getUserName().equals("") || mView.getPassword().equals("")){
            return true;
        }
        else return false;
    }
}
