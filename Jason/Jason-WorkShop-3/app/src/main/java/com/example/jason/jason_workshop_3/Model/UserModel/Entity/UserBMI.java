package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

/**
 * Created by jason on 15/06/2016.
 */
public class UserBMI {
    public String USERNAME;
    public String HEIGHT;
    public String WEIGHT;
    public String CHECKTIME;

    public UserBMI(String USERNAME, String HEIGHT, String WEIGHT, String CHECKTIME) {
        this.USERNAME = USERNAME;
        this.HEIGHT = HEIGHT;
        this.WEIGHT = WEIGHT;
        this.CHECKTIME = CHECKTIME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public float getHEIGHT() {
        return Float.parseFloat(HEIGHT)/100;
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
        float BMI = getWEIGHT()/(getHEIGHT()*getHEIGHT());
        return BMI;
    }
    public String convertBMI(float BMI){
        String _BMI = "Hello";
        if (BMI < 18.5) _BMI = "UnderWeight";
        else if (BMI < 24.9) _BMI = "Normal Weight";
        else if (BMI < 29.9) _BMI = "OverWeight";
        else _BMI = "Obesity";
        return _BMI;
    }
}
