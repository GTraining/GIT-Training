package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import android.os.AsyncTask;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.Presenter_UserManagement;
import com.example.jason.jason_workshop_3.View.MainActivity;

/**
 * Created by jason on 22/06/2016.
 */
public class Presenter_LoadMain {
    private MainActivity mView;
    private Presenter_UserManagement userManagement;
    private CurrentLogin lastLogin;

    public Presenter_LoadMain(MainActivity mView) {
        this.mView = mView;
        userManagement = new Presenter_UserManagement(mView);
        new LoadActivity().execute();
    }

    public class LoadActivity extends AsyncTask<Void, Void, Boolean>{
        boolean check = false;
        @Override
        protected Boolean doInBackground(Void... params) {
            lastLogin = userManagement.checkCurrentLogin();
            check = lastLogin.isCHECK();
            return check;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            userManagement.closeDatabase();
            if (aBoolean){
                mView.startUserMainActivity();
            }else mView.startLoginActivity();
        }
    }
}
