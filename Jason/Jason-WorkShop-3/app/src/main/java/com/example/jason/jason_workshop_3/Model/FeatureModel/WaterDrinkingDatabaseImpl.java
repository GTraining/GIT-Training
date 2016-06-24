package com.example.jason.jason_workshop_3.Model.FeatureModel;

import android.database.Cursor;

import java.util.List;

/**
 * Created by jason on 16/06/2016.
 */
public interface WaterDrinkingDatabaseImpl {
    long INSERT(WaterCup waterCup);
    long UPDATE(WaterCup waterCup);
    List<WaterCup> GETLIST(String Username);
    WaterCup GETOWN(String username, String date);
    boolean CHECKEXISTED(String username, String date);
    Cursor SETUPCURSOR();
}
