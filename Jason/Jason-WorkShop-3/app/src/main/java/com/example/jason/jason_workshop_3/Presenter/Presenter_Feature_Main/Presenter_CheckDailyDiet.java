package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.Presenter_UserManagement;
import com.example.jason.jason_workshop_3.View.FeatureView.DietFoodActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jason on 23/06/2016.
 */
public class Presenter_CheckDailyDiet {
    private Presenter_UserManagement userManagement;
    private CurrentLogin currentLogin;
    private User user;
    private DietFoodActivity mView;

    public Presenter_CheckDailyDiet(DietFoodActivity mView) {
        this.mView = mView;
        userManagement = new Presenter_UserManagement(mView);
        currentLogin = userManagement.checkCurrentLogin();
        user = userManagement.getUser(currentLogin.getUSERNAME());
    }

    public void getDiet(){

    }
}
