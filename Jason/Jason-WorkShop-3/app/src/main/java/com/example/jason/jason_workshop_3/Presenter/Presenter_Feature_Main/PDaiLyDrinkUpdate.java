package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main;

import com.example.jason.jason_workshop_3.Model.ClockModel.MClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.MCurrentDate;
import com.example.jason.jason_workshop_3.Model.FeatureModel.WaterCup;
import com.example.jason.jason_workshop_3.Model.FeatureModel.WaterDrinkingDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.View.FeatureView.DailyDrinkActivity;

/**
 * Created by jason on 24/06/2016.
 */
public class PDaiLyDrinkUpdate {
    private WaterDrinkingDatabase drinkingDatabase;
    private UserDatabase userDatabase;
    private CurrentLogin currentLogin;
    private DailyDrinkActivity mView;
    private MCurrentDate currentDate = new MCurrentDate();
    private MClockDate clockDate;
    private WaterCup waterCup;

    public PDaiLyDrinkUpdate(DailyDrinkActivity mView) {
        this.mView = mView;
        drinkingDatabase = new WaterDrinkingDatabase(mView);
        userDatabase = new UserDatabase(mView);
        userDatabase.open();
        drinkingDatabase.open();
        currentLogin = userDatabase.CheckCurrentLogin();
        clockDate = currentDate.getmClockDate();
    }

    public void addWaterCup(){
        waterCup = new WaterCup(currentLogin.getUSERNAME(), "0", clockDate.getDate());
        drinkingDatabase.INSERT(waterCup);
    }

    public void updateWaterCup(){
        waterCup = new WaterCup();
        waterCup = drinkingDatabase.GETOWN(currentLogin.getUSERNAME(), clockDate.getDate());
        int cup = Integer.parseInt(waterCup.getAMOUNTOFCUP()) + 1;
        waterCup.setAMOUNTOFCUP(String.valueOf(cup));
        drinkingDatabase.UPDATE(waterCup);
    }

    public int checkExisted(){
        if (!drinkingDatabase.CHECKEXISTED(currentLogin.getUSERNAME(), clockDate.getDate())){
            addWaterCup();
            return 0;
        }else {
            waterCup = new WaterCup();
            waterCup = drinkingDatabase.GETOWN(currentLogin.getUSERNAME(), clockDate.getDate());
            return Integer.parseInt(waterCup.getAMOUNTOFCUP());
        }
    }
}
