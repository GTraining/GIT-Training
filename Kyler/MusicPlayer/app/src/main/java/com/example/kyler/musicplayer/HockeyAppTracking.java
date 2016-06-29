package com.example.kyler.musicplayer;

import android.app.Activity;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

/**
 * Created by kyler on 29/06/2016.
 */
public class HockeyAppTracking {
    Activity activity;

    public HockeyAppTracking(Activity activity) {
        this.activity = activity;
    }

    public void checkForCrashes() {
        CrashManager.register(activity);
    }

    public void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(activity);
    }

    public void unregisterManagers() {
        UpdateManager.unregister();
    }

}
