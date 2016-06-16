package com.example.jason.jason_workshop_3.Model.WaterDrinkingModel;

import android.database.Cursor;

import java.util.List;

/**
 * Created by jason on 16/06/2016.
 */
public interface WaterDrinkingDatabaseImpl {
    long INSERT(WaterCup waterCup);
    long UPDATE(WaterCup waterCup);
    List<WaterCup> GETLIST(String Username);
    WaterCup GETOWN(WaterCup waterCup);
    Cursor SETUPCURSOR();
}
