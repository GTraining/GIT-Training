package com.example.kyler.musicplayer.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

import com.example.kyler.musicplayer.Model.Album;
import com.example.kyler.musicplayer.View.Fragment.IListAlbumView;

import java.util.ArrayList;

/**
 * Created by kyler on 27/06/2016.
 */
public class ListAlbumPresenter implements IListAlbumPresenter{

    private Context context;
    private IListAlbumView albumView;

    public ListAlbumPresenter(Context context, IListAlbumView albumView) {
        this.context = context;
        this.albumView = albumView;
    }

    @Override
    public void getAlbums() {
        albumView.loadAlbum(getAlbumsFromSDCard());
    }

    private ArrayList<Album> getAlbumsFromSDCard(){
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
        ArrayList<Album> albums = new ArrayList<>();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        for(int i=0;i<datas.size();i++){
            mmr.setDataSource(datas.get(i));
            Album album = getAlbumInfor(mmr,datas.get(i));
            boolean exist = false;
            for(int j=0;j<albums.size();j++){
                if(album.getAlbumName().equals(albums.get(j).getAlbumName())) {
                    exist = true;
                }
            }
            if(!exist){
                albums.add(album);
            }
        }
        return albums;
    }

    private Album getAlbumInfor(MediaMetadataRetriever mmr, String dataPath){
        String albumName = "";
        if( mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM) != null){
            albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        }else{
            albumName = "not available";
        }
        byte[] albumImage = mmr.getEmbeddedPicture();
        return new Album(albumName,albumImage);
    }
}
