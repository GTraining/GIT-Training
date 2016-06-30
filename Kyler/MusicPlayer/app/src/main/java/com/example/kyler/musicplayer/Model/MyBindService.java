package com.example.kyler.musicplayer.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.kyler.musicplayer.MyApplication;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;
import com.example.kyler.musicplayer.Utils.ShakeDetector;
import com.example.kyler.musicplayer.Widget.MusicPlayerWidget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MyBindService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener{
    public static final String PLAY_ACTION="android.kyler.Play";
    public static final String PREVIOUS_ACTION="android.kyler.Previous";
    public static final String NEXT_ACTION="android.kyler.Next";
    public static final String STOP_ACTION="android.kyler.Stop";
    public static int NOTIFY_ID = 0;
    public static String NOTIFICATION_TAG = "com.example.kyler.musicplayer.NotificationTag";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private ArrayList<Song> songs, shuffleSongs, normalSongs;
    private String currentPath = "";
    private int currentPosition = 0;
    private int timerTime = 0;
    private boolean shuffle = false;
    private boolean timerComplete = false;
    private boolean shake = false;
    private CountDown countDownTimer;
    private int repeat = 0;
    private Handler mHandler;
    MediaPlayer mediaPlayer;
    private BroadcastReceiver receiver;
    Notification not;
    IBinder iBinder = new MyBinder();
    public MyBindService() {
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        mSensorManager.unregisterListener(mShakeDetector);
        stopMusic();
        Intent intent = new Intent(MusicPlayerWidget.UPDATE_WIDGET);
        intent.putStringArrayListExtra(String.valueOf(R.string.path),new ArrayList<String>());
        sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return(START_NOT_STICKY);
    }

    private void sendUpdateWidgetBroadcast(){
        Intent intent = new Intent(MusicPlayerWidget.UPDATE_WIDGET);
        ArrayList<String> songPaths = Helper.getSongPaths(songs);
        intent.putStringArrayListExtra(String.valueOf(R.string.path),songPaths);
        intent.putExtra(String.valueOf(R.string.currentID),currentPosition);
        intent.putExtra(MusicPlayerWidget.PLAYING_STATUS,isPlaying());
        if(songs.size()>0) {
            intent.putExtra(MusicPlayerWidget.SONG_IMAGE,songs.get(currentPosition).getSongImage());
            intent.putExtra(MusicPlayerWidget.MUSIC_TITLE, songs.get(currentPosition).getSongTitle());
            intent.putExtra(MusicPlayerWidget.MUSIC_ARTIST, songs.get(currentPosition).getSongArtist());
        }
        getApplicationContext().sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        Log.e("MyBindService","onCreate");
        super.onCreate();
        mHandler = new Handler();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case PLAY_ACTION:
                        MyApplication.getInstance().trackEvent("Notification Widget", "Play/pause click", "Play/pause music by Notification Widget");
                        if(isPlaying()) {
                            pauseSong();
                            not = new MusicPlayerNotification(MyBindService.this, songs, currentPosition, isPlaying()).getNotification();
                        }else{
                            resumeSong();
                            not = new MusicPlayerNotification(MyBindService.this, songs, currentPosition, isPlaying()).getNotification();
                        }
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFICATION_TAG,NOTIFY_ID, not);
                        break;
                    case PREVIOUS_ACTION:
                        MyApplication.getInstance().trackEvent("Notification Widget", "Previous click", "Play previous song by Notification Widget");
                        playPrevious();
                        break;
                    case NEXT_ACTION:
                        MyApplication.getInstance().trackEvent("Notification Widget", "Next click", "Play next song by Notification Widget");
                        playNext();
                        break;
                    case STOP_ACTION:
                        MyApplication.getInstance().trackEvent("Notification Widget", "Pause click", "Pause music and hide notification");
                        pauseSong();
                        ((NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE)).cancel(NOTIFICATION_TAG,NOTIFY_ID);
                        break;
                }
            }
        };
        registerReceiver(receiver,new IntentFilter(PLAY_ACTION));
        registerReceiver(receiver,new IntentFilter(PREVIOUS_ACTION));
        registerReceiver(receiver,new IntentFilter(NEXT_ACTION));
        registerReceiver(receiver,new IntentFilter(STOP_ACTION));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if(shake) {
                    switch (count) {
                        case 2:
                            playNext();
                            break;
                        case 1:
                            if (isPlaying()) {
                                pauseSong();
                            } else {
                                resumeSong();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playNext();
    }

    @Override
    public void onPrepared(final MediaPlayer mediaPlayer) {
        timerComplete = false;
        mediaPlayer.start();
        showNotification();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return true;
    }

    public class MyBinder extends Binder {
        public MyBindService getService(){
            return MyBindService.this;
        }
    }

    private void showNotification(){
        not = new MusicPlayerNotification(this,songs,currentPosition,isPlaying()).getNotification();
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_TAG,NOTIFY_ID,not);
        startForeground(NOTIFY_ID,not);
        sendUpdateWidgetBroadcast();
    }


    public String getCurrentPath(){
        return songs.get(currentPosition).getSongPath();
    }

    public int getRepeat(){
        return repeat;
    }

    public boolean getShake(){
        return shake;
    }

    public boolean getShuffle(){
        return shuffle;
    }

    public Song getCurrentSong(){
        if(currentPosition>songs.size() || currentPosition<0){
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
        try {
            mediaPlayer.setDataSource(songs.get(currentPosition).getSongPath());
        } catch (IOException e) {
            MyApplication.getInstance().trackException(e);
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }

    public void pauseSong(){
        mediaPlayer.pause();
        showNotification();
    }

    public void resumeSong(){
        mediaPlayer.start();
        showNotification();
    }

    public void setCurrentPosition(int currentID){
        currentPosition = currentID;
    }

    public long getCurrent(){
        sendUpdateWidgetBroadcast();
        return mediaPlayer.getCurrentPosition();
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

    public void setShake(boolean shake){
        this.shake = shake;
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
                    currentPosition = 0;
                    playSong();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pauseSong();
                        }
                    },300);
                }else{
                    playSong();
                }
                break;
        }
    }

    private void stopMusic(){
        timerComplete = true;
        mediaPlayer.stop();
        stopForeground(true);
        stopSelf();
        ((NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE)).cancel(NOTIFICATION_TAG,NOTIFY_ID);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timerComplete = false;
            }
        },800);
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
        songs = new ArrayList<>();
        sendUpdateWidgetBroadcast();
        mediaPlayer.release();
        return true;
    }

    @Override
    public void onDestroy() {
        Log.e("MyBindService","onDestroy");
        unregisterReceiver(receiver);
        mSensorManager.unregisterListener(mShakeDetector);
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
                mHandler.postDelayed(new Runnable() {
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
