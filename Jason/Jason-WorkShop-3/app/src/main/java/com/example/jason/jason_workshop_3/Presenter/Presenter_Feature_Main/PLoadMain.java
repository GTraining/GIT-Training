package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import android.os.AsyncTask;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;
import com.example.jason.jason_workshop_3.View.MainActivity;

/**
 * Created by jason on 22/06/2016.
 */
public class PLoadMain {
    private MainActivity mView;
    private PUserManagement userManagement;
    private CurrentLogin lastLogin;

    public PLoadMain(MainActivity mView) {
        this.mView = mView;
        userManagement = new PUserManagement(mView);
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
