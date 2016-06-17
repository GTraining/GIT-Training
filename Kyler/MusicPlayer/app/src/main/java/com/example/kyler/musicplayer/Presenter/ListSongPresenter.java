package com.example.kyler.musicplayer.Presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.kyler.musicplayer.Model.MyService;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.Fragment.IListSongView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kyler on 15/06/2016.
 */
public class ListSongPresenter implements IListSongPresenter {
    private Context context;
    private IListSongView view;

    public ListSongPresenter(Context context, IListSongView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getSong() {
        view.loadSong(getSongs());
    }

    @Override
    public void playSong(String path) {
        Intent intent = new Intent(context,MyService.class);
        intent.putExtra(String.valueOf(R.string.path),path);
        context.startService(intent);
    }

    /**
     * get all of song from the SD card
     * @return
     */
    private ArrayList<Song> getSongs(){
        Cursor mCursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media.DATA }, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        ArrayList<String> datas = new ArrayList<String>();
        if (mCursor.moveToFirst()) {
            do {
                if(mCursor.getString(0).endsWith(".mp3")) {
                    datas.add(mCursor.getString(0));
                }
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        ArrayList<Song> result = new ArrayList<>();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        for(int i=0;i<datas.size();i++){
            mmr.setDataSource(datas.get(i));
            result.add(getSongInformation(mmr,datas.get(i)));
        }
        return result;
    }

    /**
     * get the information of audio file by ID3 Tags by using MediaMetadataRetriever
     * @param dataPath
     * @return
     */
    private Song getSongInformation(MediaMetadataRetriever mmr,String dataPath){
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String author = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR);
        String dur = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long duration = Long.parseLong(dur);
        byte[] image = mmr.getEmbeddedPicture();
        return new Song(title,artist,album,author,duration,image,dataPath);
    }

    private void playSongPath(String path){
        Intent intent = new Intent(context, MyService.class);
        intent.putExtra("path",path);
        context.startService(intent);
    }

}
