package com.example.jason.jason_workshop_3.Presenter.PresentLogin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Model.UserData.CheckLogin;
import com.example.jason.jason_workshop_3.Model.UserData.User;
import com.example.jason.jason_workshop_3.View.VLogin.LoginActivity;

/**
 * Created by jason on 13/06/2016.
 */
public class LoginAdapter implements LoginImpl{

    private User mUser;
    private UserMangement mUserManagement;
    private LoginActivity mView;
    private CheckLogin mCheckLogin;

    public LoginAdapter(LoginActivity mViews) {
        this.mView = mViews;
        mUserManagement = new UserMangement(mView);
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
                mUserManagement.UpdateLogin(mCheckLogin.getID(), "on");
                if (mCheckLogin.getStatus().equals("new")) mView.OpenNewUserActivity();
                else mView.OpenMainActivity();
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
