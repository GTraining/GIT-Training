package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import android.os.CountDownTimer;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jason on 22/06/2016.
 */
public class PLoadMain {
    private MainActivity mView;
    private PUserManagement userManagement;
    private int i = 0;
    private List<Integer> drawableList = new ArrayList<>();
    public PLoadMain(MainActivity mView) {
        this.mView = mView;
        userManagement = new PUserManagement(mView);
        drawableList.add(R.drawable.heart_pulse_green);
        drawableList.add(R.drawable.heart_pulse_red);
        drawableList.add(R.drawable.heart_pulse_purple);
        drawableList.add(R.drawable.heart_pulse_yelow);
        drawableList.add(R.drawable.heart_pulse_blue);
        drawableList.add(R.drawable.heart_pulse_black);
        drawableList.add(R.drawable.heart_pulse_white);
        Random random = new Random();
        i = random.nextInt(drawableList.size()-1);
//        SetLoading();
//        if (i < 3){
//            runCount();
//        } else
//        new LoadActivity().execute();
    }

//    public class LoadActivity extends AsyncTask<Void, Void, CurrentLogin>{
//        private CurrentLogin lastLogin;
//
//        @Override
//        protected CurrentLogin doInBackground(Void... params) {
//            for (int i = 0; i < 50 ; i ++){}
//            lastLogin = userManagement.checkCurrentLogin();
//            return lastLogin;
//        }
//
//        @Override
//        protected void onPostExecute(CurrentLogin currentLogin) {
//            super.onPostExecute(currentLogin);
//            if (currentLogin.isCHECK()) {
//                String username = currentLogin.getUSERNAME();
//                if (username.substring(0,1).equals("@")){
//                   mView.startTwitter();
//                }else mView.startUserMainActivity();
//            } else mView.startLoginActivity();
//        }
//    }
//
//    public void runCount(){
//        mHandler.postDelayed(mRunnable, 1000);
//    }

    public void Loading(int time_out) {
        new CountDownTimer(time_out, 100) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                mView.setImage(drawableList.get(i));
                if (i < (drawableList.size() - 1)) i ++;
                else i = 0;
            }
            public void onFinish() {
                cancel();
                CurrentLogin lastLogin = userManagement.checkCurrentLogin();
                if (lastLogin.isCHECK()) {
                    String username = lastLogin.getUSERNAME();
                    if (username.substring(0,1).equals("@")){
                        mView.startTwitter();
                    }else mView.startUserMainActivity();
                } else mView.startLoginActivity();
            }
        }.start();
    }
}
