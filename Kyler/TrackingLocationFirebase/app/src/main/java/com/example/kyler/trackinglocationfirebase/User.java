package com.example.kyler.trackinglocationfirebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kyler on 22/07/2016.
 */
//@IgnoreExtraProperties
public class User {
    public String username;
    public String location;
    public String dateTime;

    public User() {
    }

    public User(String username, String location, String dateTime) {
        this.username = username;
        this.location = location;
        this.dateTime = dateTime;
    }

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("username", username);
//        result.put("location", location);
//        result.put("dateTime", dateTime);
//        return result;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
