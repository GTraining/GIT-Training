package com.example.kyler.musicplayer.Presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import com.example.kyler.musicplayer.Model.MyBindService;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;
import com.example.kyler.musicplayer.View.ISongDetailView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kyler on 16/06/2016.
 */
public class SongDetailPresenter implements ISongDetailPresenter{
    private Context context;
    private ISongDetailView detailView;
    private ArrayList<Integer> backgrounds;

    boolean binded = false;
    MyBindService myBindService;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyBindService.MyBinder myIBinder = (MyBindService.MyBinder) iBinder;
            myBindService = myIBinder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binded = false;
        }
    };

    public SongDetailPresenter(Context context, ISongDetailView detailView) {
        this.context = context;
        this.detailView = detailView;
        backgrounds = new ArrayList<>();
        backgrounds.add(R.drawable.bg1);
        backgrounds.add(R.drawable.bg2);
        backgrounds.add(R.drawable.bg3);
        backgrounds.add(R.drawable.bg4);
        startService();
    }

    private void startService(){
        if(!binded){
            Intent intent = new Intent(context, MyBindService.class);
            context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
            binded = true;
        }
    }

    @Override
    public void setSongs(ArrayList<String> arrStringPaths, int currentID) {
        if(binded){
            ArrayList<Song> songs = new ArrayList<>();
            for(int i=0;i<arrStringPaths.size();i++){
                songs.add(Helper.getSong(arrStringPaths.get(i)));
            }
            myBindService.setSongs(songs);
            myBindService.setCurrentPosition(currentID);
            myBindService.playSong();
            getSong();
        }
    }

    @Override
    public void setSongs(ArrayList<String> arrStringPaths) {
        if(binded){
            ArrayList<Song> songs = new ArrayList<>();
            for(int i=0;i<arrStringPaths.size();i++){
                songs.add(Helper.getSong(arrStringPaths.get(i)));
            }
            myBindService.setSongs(songs);
        }
    }

    @Override
    public void resumeSong() {
        myBindService.resumeSong();
    }

    @Override
    public void getSong() {
        detailView.loadSong(myBindService.getCurrentSong());
    }

    @Override
    public void pauseSong() {
        myBindService.pauseSong();
    }

    @Override
    public void seekTo(long time) {
        myBindService.seekTo(time);
    }

    @Override
    public void setShuffle(boolean shuffle) {
        myBindService.setShuffle(shuffle);
    }

    @Override
    public void playNext() {
        detailView.updateBackground(backgrounds.get(new Random().nextInt(3)));
        myBindService.playNext();
        getSong();
    }

    @Override
    public void playPrevious() {
        detailView.updateBackground(backgrounds.get(new Random().nextInt(3)));
        myBindService.playPrevious();
        getSong();
    }

    @Override
    public void setTimer(int minute) {
        long time = minute * 60 * 1000;
        myBindService.setTimer(time);
    }

    @Override
    public void setRepeat(int repeat) {
        myBindService.setRepeat(repeat);
    }

    @Override
    public boolean isPlaying() {
        if(binded)
            return myBindService.isPlaying();
        else
            return false;
    }

    @Override
    public boolean getTimerComplete() {
        if(binded){
            return myBindService.getTimerComplete();
        }else{
            return true;
        }
    }

    @Override
    public int getTimerTime() {
        return myBindService.getTimerTime();
    }

    @Override
    public long getCurrent() {
        return myBindService.getCurrent();
    }

    @Override
    public String getCurrentPath() {
        return myBindService.getCurrentPath();
    }

    @Override
    public int getRepeatStatus() {
        return myBindService.getRepeat();
    }

    @Override
    public boolean getShuffleStatus() {
        return myBindService.getShuffle();
    }

    @Override
    public void onFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(binded) {
                    context.unbindService(serviceConnection);
                    binded = false;
                }
            }
        },3000);
    }

}
