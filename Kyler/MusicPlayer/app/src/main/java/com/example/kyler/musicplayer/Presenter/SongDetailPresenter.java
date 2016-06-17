package com.example.kyler.musicplayer.Presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.MediaController;

import com.example.kyler.musicplayer.Model.MyBindService;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.Utils.Helper;
import com.example.kyler.musicplayer.View.ISongDetailView;

/**
 * Created by kyler on 16/06/2016.
 */
public class SongDetailPresenter implements ISongDetailPresenter, MediaController.MediaPlayerControl{
    private Context context;
    private ISongDetailView detailView;

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
    }

    @Override
    public void getSong(String path) {
        Song song = Helper.getSong(path);
        detailView.loadSong(song);
    }

    @Override
    public void playSong(Song song) {
        if(!binded) {
            Intent intent = new Intent(context, MyBindService.class);
            intent.putExtra(String.valueOf(R.string.path), song.getSongPath());
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            binded = true;
        }else{
            myBindService.resumeSong();
        }
    }

    @Override
    public void stopSong() {
        if(binded){
            myBindService.stopSong();
        }
    }

    @Override
    public void seekTo(long time) {
        if(binded){
            myBindService.forwardTo(time);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int i) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
