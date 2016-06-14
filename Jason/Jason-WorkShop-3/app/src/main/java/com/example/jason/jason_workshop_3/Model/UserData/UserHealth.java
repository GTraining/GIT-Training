package com.example.jason.jason_workshop_3.Model.UserData;

import android.widget.Switch;

/**
 * Created by jason on 13/06/2016.
 */
public class UserHealth {
    public String Username;
    public String age;
    public String sex;
    public double Height;
    public double Weight;

    public UserHealth(String username, String age, String sex, double height, double weight) {
        Username = username;
        this.age = age;
        this.sex = sex;
        Height = height;
        Weight = weight;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public double convertBMI(){
        double BMI = getWeight()/(getHeight()*getHeight());
        return BMI;
    }
    public String getBMI(double BMI){
        String _BMI ="";
        if (BMI < 18.5) _BMI = "UnderWeight";
        else if (BMI < 24.9) _BMI = "Normal Weight";
        else if (BMI < 29.9) _BMI = "OverWeight";
        else _BMI = "Obesity";
        return _BMI;
    }
}
