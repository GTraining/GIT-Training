package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

/**
 * Created by jason on 21/06/2016.
 */
public class UserSetUp {
    public String USERNAME;
    public String USER_CHECK_BMI_TYPE; // MONTHLY : QUARTERLY
    public String DAILY_DRINK_ALARM; // value: 1 - true : 0 - false
    public String DAILY_FITNESS_ALARM; // value: 1 - true : 0 - false
    public String DAILY_DIET_ALARM; // value: 1 - true : 0 - false

    public UserSetUp(String USERNAME, String USER_CHECK_BMI_TYPE, String DAILY_DRINK_ALARM,
                     String DAILY_FITNESS_ALARM, String DAILY_DIET_ALARM) {
        this.USERNAME = USERNAME;
        this.USER_CHECK_BMI_TYPE = USER_CHECK_BMI_TYPE;
        this.DAILY_DRINK_ALARM = DAILY_DRINK_ALARM;
        this.DAILY_FITNESS_ALARM = DAILY_FITNESS_ALARM;
        this.DAILY_DIET_ALARM = DAILY_DIET_ALARM;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getUSER_CHECK_BMI_TYPE() {
        return USER_CHECK_BMI_TYPE;
    }

    public void setUSER_CHECK_BMI_TYPE(String USER_CHECK_BMI_TYPE) {
        this.USER_CHECK_BMI_TYPE = USER_CHECK_BMI_TYPE;
    }

    public String getDAILY_DRINK_ALARM() {
        return DAILY_DRINK_ALARM;
    }

    public void setDAILY_DRINK_ALARM(String DAILY_DRINK_ALARM) {
        this.DAILY_DRINK_ALARM = DAILY_DRINK_ALARM;
    }

    public String getDAILY_FITNESS_ALARM() {
        return DAILY_FITNESS_ALARM;
    }

    public void setDAILY_FITNESS_ALARM(String DAILY_FITNESS_ALARM) {
        this.DAILY_FITNESS_ALARM = DAILY_FITNESS_ALARM;
    }

    public String getDAILY_DIET_ALARM() {
        return DAILY_DIET_ALARM;
    }

    public void setDAILY_DIET_ALARM(String DAILY_DIET_ALARM) {
        this.DAILY_DIET_ALARM = DAILY_DIET_ALARM;
    }
}
