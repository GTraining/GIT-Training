package com.example.kyler.musicplayer.Presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.kyler.musicplayer.Model.MyBindService;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.View.ISongDetailView;

/**
 * Created by kyler on 16/06/2016.
 */
public class SongDetailPresenter implements ISongDetailPresenter{
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
    public void getSong() {

    }

    @Override
    public void playSong(Song song) {

    }

    @Override
    public void stopSong() {

    }
}
