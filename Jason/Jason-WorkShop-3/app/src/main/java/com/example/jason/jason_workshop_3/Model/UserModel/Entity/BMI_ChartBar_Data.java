package com.example.jason.jason_workshop_3.Model.UserModel.Entity;

/**
 * Created by jason on 21/06/2016.
 */
public class BMI_ChartBar_Data {
    public float Collumn;
    public String Header;

    public BMI_ChartBar_Data(float collumn, String header) {
        Collumn = collumn;
        Header = header;
    }

    public float getCollumn() {
        return Collumn;
    }

    public void setCollumn(float collumn) {
        Collumn = collumn;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }
}
