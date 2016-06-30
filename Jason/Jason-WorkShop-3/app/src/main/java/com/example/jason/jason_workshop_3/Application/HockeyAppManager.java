package com.example.jason.jason_workshop_3.Application;

import android.app.Activity;
import android.content.Context;

import com.example.jason.jason_workshop_3.R;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.UpdateManager;

/**
 * Created by jason on 29/06/2016.
 */
public class HockeyAppManager {
    private Activity activity;

    public HockeyAppManager(Activity activity) {
        this.activity = activity;
    }

    public void checkForCrashes() {
        CrashManager.register(activity, "d4395d0724924ebf89618841afa9266d", new MyCustomCrashManagerListener());
    }

    public void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(activity);
    }

    public void unregisterManagers() {
        UpdateManager.unregister();
    }
    public class MyCustomCrashManagerListener extends CrashManagerListener {
        @Override
        public boolean shouldAutoUploadCrashes() {
            return true;
        }
    }
}
