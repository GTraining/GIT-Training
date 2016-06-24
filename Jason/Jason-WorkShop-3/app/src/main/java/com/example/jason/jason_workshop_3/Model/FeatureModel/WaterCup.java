package com.example.jason.jason_workshop_3.Model.FeatureModel;

/**
 * Created by jason on 16/06/2016.
 */
public class WaterCup {
    public String USERNAME;
    public String AMOUNTOFCUP;
    public String DATE;

    public WaterCup(){

    }
    public WaterCup(String USERNAME, String AMOUNTOFCUP, String DATE) {
        this.USERNAME = USERNAME;
        this.AMOUNTOFCUP = AMOUNTOFCUP;
        this.DATE = DATE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getAMOUNTOFCUP() {
        return AMOUNTOFCUP;
    }

    public void setAMOUNTOFCUP(String AMOUNTOFCUP) {
        this.AMOUNTOFCUP = AMOUNTOFCUP;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }
}
