package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PDailyDiet;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;
import com.example.jason.jason_workshop_3.View.FeatureView.FoodActivity;

/**
 * Created by jason on 23/06/2016.
 */
public class PCheckDailyDiet {
    private PUserManagement userManagement;
    private CurrentLogin currentLogin;
    private User user;
    private FoodActivity mView;

    public PCheckDailyDiet(FoodActivity mView) {
        this.mView = mView;
        userManagement = new PUserManagement(mView);
        currentLogin = userManagement.checkCurrentLogin();
        user = userManagement.getUser(currentLogin.getUSERNAME());
    }

    public void getDiet(){

    }
}
