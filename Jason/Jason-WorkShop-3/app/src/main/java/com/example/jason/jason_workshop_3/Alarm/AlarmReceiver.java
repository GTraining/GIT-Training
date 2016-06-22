package com.example.jason.jason_workshop_3.Alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.MonthlyCheckBMIActivity;

/**
 * Created by jason on 22/06/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in receiver", "Jason!");

        Intent myIntent = new Intent();
        String title = intent.getStringExtra("Intent");
        String contentText = intent.getStringExtra("ContentText");
        myIntent = new Intent(context, MonthlyCheckBMIActivity.class);
        myIntent.putExtra("Intent", "3");
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(pIntent)
                        .setSmallIcon(R.drawable.bell_ring)
                        .setContentTitle(title)
                        .setContentText(contentText);
        notificationManager.notify(0, mBuilder.build());
    }
}
