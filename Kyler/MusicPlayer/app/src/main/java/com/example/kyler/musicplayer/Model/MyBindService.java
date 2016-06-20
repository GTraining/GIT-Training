package com.example.kyler.musicplayer.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;
import com.example.kyler.musicplayer.View.SongDetailActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MyBindService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{
    private ArrayList<Song> songs;
    private String currentPath = "";
    private int currentPosition = 0;
    private int NOTIFY_ID = 0;
    private String songTitle = "";
    private boolean shuffle = false;
    private Random random;
    MediaPlayer mediaPlayer;
    IBinder iBinder = new MyBinder();
    public MyBindService() {
    }

    @Override
    public void onCreate() {
        Log.e("MyBindService","onCreate");
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        random = new Random();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        Intent notIntent = new Intent(this, SongDetailActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setSmallIcon(R.drawable.play)
//                .setTicker(songTitle)
//                .setOngoing(true)
//                .setContentTitle("Playing")
//                .setContentText(songTitle);
//        Notification not = builder.build();
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFY_ID,not);
        showNotify();
    }

    private void showNotify(){
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification not = builder.build();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID,not);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.v("MUSIC PLAYER", "Playback Error");
        mediaPlayer.reset();
        return false;
    }

    public class MyBinder extends Binder {
        public MyBindService getService(){
            return MyBindService.this;
        }
    }

    public Song getCurrentSong(){
        return songs.get(currentPosition);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind Service",currentPath);
        return iBinder;
    }

    public void setSongs(ArrayList<Song> songs){
        this.songs = songs;
    }

    public void seekTo(long time){
        Log.e("MyBindService","seekTo");
        mediaPlayer.seekTo((int) time);
    }

    public void playSong(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(songs.get(currentPosition).getSongPath());
            songTitle = songs.get(currentPosition).getSongTitle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }

    public void pauseSong(){
        mediaPlayer.pause();
        ((NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE)).cancel(NOTIFY_ID);
    }

    public void resumeSong(){
        mediaPlayer.start();
        showNotify();
    }

    public void setCurrentPosition(int currentID){
        currentPosition = currentID;
    }

    public long getCurrent(){
        return mediaPlayer.getCurrentPosition();
    }

    public long getDuration(){
        return mediaPlayer.getDuration();
    }

    public Song getSong(){
        return songs.get(currentPosition);
    }

    public void playNext(){
        if(shuffle){
            int newSong = currentPosition;
            while(newSong==currentPosition){
                newSong=random.nextInt(songs.size());
            }
            currentPosition=newSong;
        }
        else{
            currentPosition++;
            if(currentPosition>=songs.size()) currentPosition=0;
        }
        playSong();
    }

    public void setShuffle(boolean shuffle){
        this.shuffle = shuffle;
    }

    public void playPrevious(){
        currentPosition--;
        if(currentPosition<0) currentPosition=songs.size()-1;
        playSong();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MyBindService","onUnBind");
        mediaPlayer.stop();
        mediaPlayer.release();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("MyBindService","onDestroy");
        super.onDestroy();
        stopForeground(true);
    }



    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
}
