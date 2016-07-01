package com.example.kyler.musicplayer.Model;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.SongDetailActivity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by kyler on 23/06/2016.
 */
public class MusicPlayerNotification {
    Context context;
    ArrayList<Song> songs;
    int currentPosition;
    boolean playing = false;

    public MusicPlayerNotification(Context context, ArrayList<Song> songs, int currentPosition, boolean playing){
        this.context = context;
        this.songs = songs;
        this.currentPosition = currentPosition;
        this.playing = playing;
    }

    public Notification getNotification(){
        Intent notIntent = new Intent(context, SongDetailActivity.class);
        notIntent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        ArrayList<String> arrSongPaths = new ArrayList<>();
        for(int j=0;j<songs.size();j++){
            arrSongPaths.add(songs.get(j).getSongPath());
        }
        notIntent.putStringArrayListExtra(String.valueOf(R.string.path),arrSongPaths);
        notIntent.putExtra(String.valueOf(R.string.currentID),currentPosition);
        PendingIntent pendInt = PendingIntent.getActivity(context, 0,
                notIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        RemoteViews remoteViews = getNotificationRemote();
        RemoteViews smallRemoteViews = getNotificationSmallRemote();

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.play)
                .setContentIntent(pendInt)
                .setTicker(songs.get(currentPosition).getSongTitle())
                .setOngoing(true);
//                .setContentTitle(songs.get(currentPosition).getSongTitle())
//                .setContentText(songs.get(currentPosition).getSongArtist());
        Notification notification = builder.build();
        notification.contentView = smallRemoteViews;
        notification.bigContentView = remoteViews;
        return notification;
    }

    private RemoteViews getNotificationRemote(){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_remote);
        remoteViews.setTextViewText(R.id.notification_title,songs.get(currentPosition).getSongTitle());
        remoteViews.setTextViewText(R.id.notification_artist,songs.get(currentPosition).getSongArtist());
        byte[] img = songs.get(currentPosition).getSongImage();
        if(img != null){
            InputStream is = new ByteArrayInputStream(img);
            Bitmap bm = BitmapFactory.decodeStream(is);
            remoteViews.setImageViewBitmap(R.id.notification_image,bm);
        }else{
            remoteViews.setImageViewResource(R.id.notification_image,R.drawable.defaultpic);
        }
        Intent playIntent = new Intent(MyBindService.PLAY_ACTION);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context,0,playIntent,0);
        Intent previousIntent = new Intent(MyBindService.PREVIOUS_ACTION);
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(context,0,previousIntent,0);
        Intent nextIntent = new Intent(MyBindService.NEXT_ACTION);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context,0,nextIntent,0);
        Intent stopIntent = new Intent(MyBindService.STOP_ACTION);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context,0,stopIntent,0);
        remoteViews.setOnClickPendingIntent(R.id.notification_play_icon,playPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.notification_previous_icon,previousPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.notification_next_icon,nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.notification_close_icon,stopPendingIntent);
        if(playing) {
            remoteViews.setImageViewResource(R.id.notification_play_icon, R.drawable.notification_pauseicon);
        }else{
            remoteViews.setImageViewResource(R.id.notification_play_icon, R.drawable.notification_playicon);
        }
        return remoteViews;
    }

    private RemoteViews getNotificationSmallRemote(){
        RemoteViews smallRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_remote_small);
        smallRemoteViews.setTextViewText(R.id.notification_small_title,songs.get(currentPosition).getSongTitle());
        smallRemoteViews.setTextViewText(R.id.notification_small_artist,songs.get(currentPosition).getSongArtist());
        byte[] img = songs.get(currentPosition).getSongImage();
        if(img != null){
            InputStream is = new ByteArrayInputStream(img);
            Bitmap bm = BitmapFactory.decodeStream(is);
            smallRemoteViews.setImageViewBitmap(R.id.notification_small_image,bm);
        }else{
            smallRemoteViews.setImageViewResource(R.id.notification_small_image,R.drawable.defaultpic);
        }
        Intent playIntent = new Intent(MyBindService.PLAY_ACTION);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context,0,playIntent,0);
        Intent nextIntent = new Intent(MyBindService.NEXT_ACTION);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context,0,nextIntent,0);
        smallRemoteViews.setOnClickPendingIntent(R.id.notification_small_play,playPendingIntent);
        smallRemoteViews.setOnClickPendingIntent(R.id.notification_small_next,nextPendingIntent);
        if(playing) {
            smallRemoteViews.setImageViewResource(R.id.notification_small_play, R.drawable.notification_pauseicon);
        }else{
            smallRemoteViews.setImageViewResource(R.id.notification_small_play, R.drawable.notification_playicon);
        }
        return smallRemoteViews;
    }
}
