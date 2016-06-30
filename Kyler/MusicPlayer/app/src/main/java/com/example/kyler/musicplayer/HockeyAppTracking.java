package com.example.kyler.musicplayer;

import android.app.Activity;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

/**
 * Created by kyler on 29/06/2016.
 */
public class HockeyAppTracking {

    public static void checkForCrashes(Activity activity) {
        CrashManager.register(activity);
    }

    public static void checkForUpdates(Activity activity) {
        // Remove this for store builds!
        UpdateManager.register(activity);
    }

    public static void unregisterManagers(Activity activity) {
        UpdateManager.unregister();
    }

}
