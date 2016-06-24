package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

/**
 * Created by jason on 15/06/2016.
 */
public class UserBMI {
    public String USERNAME;
    public String AGE;
    public String HEIGHT;
    public String WEIGHT;
    public String CHECKTIME;

    public UserBMI(String USERNAME, String AGE, String HEIGHT, String WEIGHT, String CHECKTIME) {
        this.USERNAME = USERNAME;
        this.AGE = AGE;
        this.HEIGHT = HEIGHT;
        this.WEIGHT = WEIGHT;
        this.CHECKTIME = CHECKTIME;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public float getHEIGHT() {
        return Float.parseFloat(HEIGHT);
    }

    public void setHEIGHT(String HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public float getWEIGHT() {
        return Float.parseFloat(WEIGHT);
    }

    public void setWEIGHT(String WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public String getCHECKTIME() {
        return CHECKTIME;
    }

    public void setCHECKTIME(String CHECKTIME) {
        this.CHECKTIME = CHECKTIME;
    }
    public float getBMI(){
        float BMI = getWEIGHT()/(getHEIGHT()/100*getHEIGHT()/100);
        return BMI;
    }
    public String convertBMI(float BMI){
        String _BMI = "Do You make sure about your BMI?";
        if (BMI < 18.5) _BMI = "UnderWeight";
        else if (BMI < 24.9) _BMI = "Normal Weight";
        else if (BMI < 29.9) _BMI = "OverWeight";
        else if (BMI < 50) _BMI = "Obesity";
        return _BMI;
    }
}
