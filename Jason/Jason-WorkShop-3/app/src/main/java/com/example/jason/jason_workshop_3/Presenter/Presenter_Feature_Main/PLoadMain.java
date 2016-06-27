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


    public PLoadMain(MainActivity mView) {
        this.mView = mView;
        userManagement = new PUserManagement(mView);
        new LoadActivity().execute();
    }

    public class LoadActivity extends AsyncTask<Void, Void, CurrentLogin>{
        private CurrentLogin lastLogin;

        @Override
        protected CurrentLogin doInBackground(Void... params) {
            lastLogin = userManagement.checkCurrentLogin();
            return lastLogin;
        }

        @Override
        protected void onPostExecute(CurrentLogin currentLogin) {
            super.onPostExecute(currentLogin);
            if (currentLogin.isCHECK()) {
                String username = currentLogin.getUSERNAME();
                if (username.substring(0,1).equals("@")){
                   mView.startTwitter();
                }else mView.startUserMainActivity();
            } else mView.startLoginActivity();
        }
    }
}
