package com.example.jason.jason_workshop_3.Model.UserData;

/**
 * Created by jason on 13/06/2016.
 */
public class UserHealth {
    public String Username;
    public String Name;
    public String age;
    public double Height;
    public double Weight;

    public UserHealth(String name, String age, double height, double weight) {
        Name = name;
        this.age = age;
        Height = height;
        Weight = weight;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getUserBMI(){
        double BMI = getWeight()/(getHeight()*getHeight());
        if (BMI<18.5) return "Leanness";
        if (BMI > 18.5 && BMI < 24) return "Normal";
        else return "Fat";
    }
}
