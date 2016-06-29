package com.example.jason.jason_workshop_3.View.FeatureView.Alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.MainActivity;

import java.util.Calendar;

/**
 * Created by jason on 28/06/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Set Notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notif = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.bell_ring)
                .setContentTitle("DAILY DRINK!")
                .setContentText("Time to dink water!")
                .setLights(context.getResources().getColor(R.color.Red), 200, 200)
                .setSound(Uri.EMPTY);
        //Set wake up screen
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");

        //Set calendar
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//
//        //Check Period of Alarm
//        int currentHour = calendar.get(Calendar.HOUR);
//        int currentMinute = calendar.get(Calendar.MINUTE);
//        long currentTime = 1000 * 60 * currentHour * currentMinute;
//        long startTime = Long.parseLong(intent.getStringExtra("START"));
//        long endTime = Long.parseLong(intent.getStringExtra("END"));

        //Start Alarm
//        if (currentTime > startTime && currentTime < endTime){
            notificationManager.notify(1, notif.build());
            wakeLock.acquire();
//        }


    }
}
