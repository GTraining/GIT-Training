package com.example.kyler.musicplayer.Model;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;

import java.io.IOException;

public class MyBindService extends Service implements MediaPlayer.OnCompletionListener{
    public static String ACTION_MUSIC_COMPLETE = "ACTION_MUSIC_COMPLETE";

    String currentPath = "";
    MediaPlayer mediaPlayer;
    IBinder iBinder = new MyBinder();
    public MyBindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    public class MyBinder extends Binder {
        public MyBindService getService(){
            return MyBindService.this;
        }
    }

    public Song getCurrentSong(){
        return Helper.getSong(currentPath);
    }

    @Override
    public IBinder onBind(Intent intent) {
        currentPath = intent.getStringExtra(String.valueOf(R.string.path));
        Log.e("Bind Service",currentPath);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(currentPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return iBinder;
    }

    public void forwardTo(long time){
        Log.e("MyBindService","forwardTo");
        mediaPlayer.seekTo((int) time);
    }

    public void stopSong(){
        mediaPlayer.pause();
    }

    public void resumeSong(){
        mediaPlayer.start();
    }

    public long getCurrent(){
        return mediaPlayer.getCurrentPosition();
    }

    public long getDuration(){
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MyBindService","onUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("MyBindService","onDestroy");
        super.onDestroy();
        mediaPlayer.release();
    }



    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
}
