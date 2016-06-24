package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import com.example.jason.jason_workshop_3.ChartLibrary.Score;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;
import com.example.jason.jason_workshop_3.View.FeatureView.BMIChartActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 22/06/2016.
 */
public class PBMIChartLine {
    private BMIChartActivity mView;
    private PUserManagement userManagement;
    private UserBMIDatabase userBMIDatabase;
    private CurrentLogin mCurrentLogin;
    private List<UserBMI> userBMIList = new ArrayList<>();

    public PBMIChartLine(BMIChartActivity mView) {
        this.mView = mView;
        userBMIDatabase = new UserBMIDatabase(mView);
        userManagement = new PUserManagement(mView);
        userBMIDatabase.open();
        mCurrentLogin = userManagement.checkCurrentLogin();
        userBMIList = userBMIDatabase.GETLIST(mCurrentLogin.getUSERNAME());
    }

    public List<Score> setScore(){
        List<Score> scoreList = new ArrayList<>();
        for (int i = 0; i < userBMIList.size(); i ++){
            float row_value = userBMIList.get(i).getBMI();
            String column_value = userBMIList.get(i).getCHECKTIME();
            scoreList.add(new Score(row_value, i, column_value));
        }
        return scoreList;
    }
}
