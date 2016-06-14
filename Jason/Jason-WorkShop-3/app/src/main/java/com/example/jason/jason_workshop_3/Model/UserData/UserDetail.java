package com.example.jason.jason_workshop_3.Model.UserData;

/**
 * Created by jason on 13/06/2016.
 */
public class UserDetail {
    public String Username;
    public String Name;
    public int age;
    public boolean sex;
    public double height;
    public double weight;

    public UserDetail(String username, String name, int age, boolean sex, double height, double weight) {
        Username = username;
        Name = name;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
    }
}
