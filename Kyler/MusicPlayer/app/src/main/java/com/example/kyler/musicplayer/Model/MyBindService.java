package com.example.kyler.musicplayer.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyBindService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{
    public static final String PLAY_ACTION="android.kyler.Play";
    public static final String PREVIOUS_ACTION="android.kyler.Previous";
    public static final String NEXT_ACTION="android.kyler.Next";
    public static final String STOP_ACTION="android.kyler.Stop";
    public static int NOTIFY_ID = 0;
    private ArrayList<Song> songs, shuffleSongs, normalSongs;
    private String currentPath = "";
    private int currentPosition = 0;
    private int timerTime = 0;
    private boolean shuffle = false;
    private String songTitle = "";
    private Random random;
    private boolean timerComplete = false, complete = false;
    private CountDown countDownTimer;
    private int repeat = 0;
    MediaPlayer mediaPlayer;
    private BroadcastReceiver receiver;
    Notification not;
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
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case PLAY_ACTION:
                        if(isPlaying()) {
                            pauseSong();
                            not = new MusicPlayerNotification(MyBindService.this, songs, currentPosition, isPlaying()).getNotification();
                        }else{
                            resumeSong();
                            not = new MusicPlayerNotification(MyBindService.this, songs, currentPosition, isPlaying()).getNotification();
                        }
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFY_ID, not);
                        break;
                    case PREVIOUS_ACTION:
                        playPrevious();
                        break;
                    case NEXT_ACTION:
                        playNext();
                        break;
                    case STOP_ACTION:
                        stopMusic();
                        break;
                }
            }
        };
        registerReceiver(receiver,new IntentFilter(PLAY_ACTION));
        registerReceiver(receiver,new IntentFilter(PREVIOUS_ACTION));
        registerReceiver(receiver,new IntentFilter(NEXT_ACTION));
        registerReceiver(receiver,new IntentFilter(STOP_ACTION));
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        complete = true;
    }

    @Override
    public void onPrepared(final MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        timerComplete = false;
//        Intent notIntent = new Intent(this, SongDetailActivity.class);
//        notIntent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
//        ArrayList<String> arrSongPaths = new ArrayList<>();
//        for(int j=0;j<songs.size();j++){
//            arrSongPaths.add(songs.get(j).getSongPath());
//        }
//        notIntent.putStringArrayListExtra(String.valueOf(R.string.path),arrSongPaths);
//        notIntent.putExtra(String.valueOf(R.string.currentID),currentPosition);
//        notIntent.putExtra(String.valueOf(R.string.repeatStatus),repeat);
//        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
//                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setSmallIcon(R.drawable.play)
//                .setContentIntent(pendInt)
//                .setTicker(songTitle)
//                .setOngoing(true)
//                .setContentTitle("Playing")
//                .setContentText(songTitle);
//        Notification not = builder.build();
        not = new MusicPlayerNotification(this,songs,currentPosition,isPlaying()).getNotification();
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

    public String getCurrentPath(){
        return songs.get(currentPosition).getSongPath();
    }

    public int getRepeat(){
        return repeat;
    }

    public boolean getShuffle(){
        return shuffle;
    }

    public Song getCurrentSong(){
        if(currentPosition>2 || currentPosition<0){
            return songs.get(0);
        }
        return songs.get(currentPosition);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind Service",currentPath);
        return iBinder;
    }

    public void setSongs(ArrayList<Song> songs){
        this.songs = songs;
        this.normalSongs = songs;
    }

    public void seekTo(long time){
        Log.e("MyBindService","seekTo");
        mediaPlayer.seekTo((int) time);
    }

    public void playSong(){
        mediaPlayer.reset();
        complete = false;
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
    }

    public void resumeSong(){
        mediaPlayer.start();
    }

    public void setCurrentPosition(int currentID){
        currentPosition = currentID;
    }

    public long getCurrent(){
        long result = 0;
        if(complete) {
            result = songs.get(currentPosition).getSongDuration();
        } else {
            result = mediaPlayer.getCurrentPosition();
        }
        return result;
    }

    public Song getSong(){
        return songs.get(currentPosition);
    }


    public void setShuffle(boolean shuffle){
        this.shuffle = shuffle;
        Song song = songs.get(currentPosition);
        shuffleSongs = songs;
        if(shuffle){
            Collections.shuffle(shuffleSongs);
            songs = shuffleSongs;
        }else{
            songs = normalSongs;
        }
        for (int i=0;i<songs.size();i++){
            if(songs.get(i).getSongPath().equals(song.getSongPath())){
                currentPosition = i;
                break;
            }
        }
    }

    public void setRepeat(int repeat){
        this.repeat = repeat;
    }

    public void playNext(){
        switch (repeat){
            case 0:
                currentPosition++;
                if(currentPosition>=songs.size()) {
                    currentPosition = 0;
                    if(shuffle){
                        shuffleSongs = songs;
                        Collections.shuffle(shuffleSongs);
                        songs = shuffleSongs;
                    }else{
                        songs = normalSongs;
                    }
                }
                playSong();
                break;
            case 1:
                playSong();
                break;
            case 2:
                currentPosition++;
                if(currentPosition>=songs.size()) {
                    stopMusic();
                }
                break;
        }
    }

    private void stopMusic(){
        timerComplete = true;
        mediaPlayer.stop();
        stopSelf();
        ((NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE)).cancelAll();
    }

    public void playPrevious(){
        switch (repeat){
            case 0:
                currentPosition--;
                if(currentPosition<0) currentPosition = songs.size()-1;
                break;
            case 1:
                break;
            case 2:
                currentPosition--;
                if(currentPosition<0) currentPosition++;
                break;
        }
        playSong();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MyBindService","onUnBind");
        mediaPlayer.stop();
        mediaPlayer.release();
        return true;
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

    /**
     * set time for stoping the music, if the time input == 0, it means the user cancle the timer
     * @param time
     */
    public void setTimer(long time){
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (time == 0) {
            countDownTimer = null;
            timerTime = 0;
        } else {
            timerComplete = false;
            countDownTimer = new CountDown(time, 60000);
            if(time == 60000) {
                timerTime = 1;
            }
            countDownTimer.start();
        }
    }

    public int getTimerTime(){
        return timerTime;
    }
    public boolean getTimerComplete(){
        return timerComplete;
    }

    public class CountDown extends CountDownTimer{

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            timerTime = (int) ((l + 60000) / 60000);

            //Because of not being called on Tick in last time. Using handle to decrease timerTime
            if(timerTime == 2){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timerTime--;
                    }
                },60000);
            }
        }

        @Override
        public void onFinish() {
            timerTime = 0;
            stopMusic();
        }
    }
}
