package com.example.kyler.musicplayer.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.kyler.musicplayer.Model.MyBindService;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.MainActivity;
import com.example.kyler.musicplayer.View.SongDetailActivity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class MusicPlayerWidget extends AppWidgetProvider {
    public static final String UPDATE_WIDGET="com.example.kyler.musicplayer.Widget.UPDATE_WIDGET";
    public static final String PLAYING_STATUS="PlayMusicStatus";
    public static final String SONG_IMAGE="SongImage";
    public static final String MUSIC_TITLE="MusicTitle";
    public static final String MUSIC_ARTIST="MusicArtist";

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = createRemoteView(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private RemoteViews createRemoteView(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.music_player_widget);
        Intent playIntent = new Intent(MyBindService.PLAY_ACTION);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context,0,playIntent,0);
        Intent previousIntent = new Intent(MyBindService.PREVIOUS_ACTION);
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(context,0,previousIntent,0);
        Intent nextIntent = new Intent(MyBindService.NEXT_ACTION);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context,0,nextIntent,0);
        Intent openAppIntent = new Intent(context, MainActivity.class);
        openAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(context,0,openAppIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_play,playPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.widget_previous,previousPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.widget_next,nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.widget_background,openAppPendingIntent);
        return remoteViews;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        switch(intent.getAction()){
            case UPDATE_WIDGET:
                final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName name = new ComponentName(context, MusicPlayerWidget.class);
                int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
                final int N = appWidgetId.length;
                if (N < 1)
                {
                    return ;
                }
                else {
                    for(int i=0;i<N;i++) {
                        int id = appWidgetId[i];
                        onUpdateWidget(context, intent, appWidgetManager, id);
                    }
                }
                break;
            default:break;
        }
    }

    private void onUpdateWidget(Context context, Intent intent, AppWidgetManager appWidgetManager, int appWidgetId){
//        Log.e("Update Widget","Tracking update widget");
        boolean playing = intent.getBooleanExtra(PLAYING_STATUS,false);
        ArrayList<String> songPaths = intent.getStringArrayListExtra(String.valueOf(R.string.path));
        int currentID = intent.getIntExtra(String.valueOf(R.string.currentID),0);
        byte[] img = intent.getByteArrayExtra(SONG_IMAGE);
        String title = intent.getStringExtra(MUSIC_TITLE);
        String artist = intent.getStringExtra(MUSIC_ARTIST);
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.music_player_widget);
        if(playing){
            remoteView.setImageViewResource(R.id.widget_play,R.drawable.widget_pauseicon);
            if(img != null){
                InputStream is = new ByteArrayInputStream(img);
                Bitmap bm = BitmapFactory.decodeStream(is);
                remoteView.setImageViewBitmap(R.id.widget_image,bm);
            }else{
                remoteView.setImageViewResource(R.id.widget_image,R.drawable.defaultpic);
            }
            remoteView.setTextViewText(R.id.widget_title,title);
            remoteView.setTextViewText(R.id.widget_artist,artist);
        }else{
            remoteView.setImageViewResource(R.id.widget_play,R.drawable.widget_playicon);
        }
        PendingIntent openAppPendingIntent = null;
        if(songPaths.size()>0){
            Intent openAppIntent = new Intent(context, SongDetailActivity.class);
            openAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            openAppIntent.putStringArrayListExtra(String.valueOf(R.string.path),songPaths);
            openAppIntent.putExtra(String.valueOf(R.string.currentID),currentID);
            openAppPendingIntent = PendingIntent.getActivity(context,0,openAppIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setViewVisibility(R.id.widget_previous, View.VISIBLE);
            remoteView.setViewVisibility(R.id.widget_play, View.VISIBLE);
            remoteView.setViewVisibility(R.id.widget_next, View.VISIBLE);
        }else{
            Intent openAppIntent = new Intent(context, MainActivity.class);
            openAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            openAppPendingIntent = PendingIntent.getActivity(context,0,openAppIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setImageViewResource(R.id.widget_image,R.drawable.defaultpic);
            remoteView.setTextViewText(R.id.widget_title,"Music Player");
            remoteView.setTextViewText(R.id.widget_artist,"Designed by Kyler");
            remoteView.setViewVisibility(R.id.widget_previous, View.GONE);
            remoteView.setViewVisibility(R.id.widget_play, View.GONE);
            remoteView.setViewVisibility(R.id.widget_next, View.GONE);
        }
        remoteView.setOnClickPendingIntent(R.id.widget_background,openAppPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteView);
    }
}

