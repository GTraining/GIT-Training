package com.example.kyler.musicplayer.View.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyler.musicplayer.Model.Database.DatabaseHandler;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.Presenter.IListSongAdapterPresenter;
import com.example.kyler.musicplayer.Presenter.ListSongAdapterPresenter;
import com.example.kyler.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by kyler on 15/06/2016.
 */
public class ListSongAdapter extends BaseAdapter implements IListSongAdapter {
    Activity context;
    ArrayList<Song> songs;
    private IListSongAdapterPresenter adapterPresenter;
    private boolean favoriteMode = false;

    public ListSongAdapter(Activity context, ArrayList<Song> songs, boolean favoriteMode) {
        this.context = context;
        this.songs = songs;
        this.favoriteMode = favoriteMode;
        adapterPresenter = new ListSongAdapterPresenter(context,this);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return songs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ListSongViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_list_song_item,null);
            viewHolder = new ListSongViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.list_song_item_title);
            viewHolder.artist = (TextView) view.findViewById(R.id.list_song_item_artist);
            viewHolder.favorite = (ImageView) view.findViewById(R.id.list_song_item_favorite);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ListSongViewHolder) view.getTag();
        }
        viewHolder.title.setText(songs.get(i).getSongTitle());
        viewHolder.artist.setText(songs.get(i).getSongArtist());
        final boolean isFavor = adapterPresenter.isFavoriteSong(songs.get(i));
        if(isFavor){
            viewHolder.favorite.setImageResource(R.drawable.favoritebutton);
        }else{
            viewHolder.favorite.setImageResource(R.drawable.unfavoritebutton);
        }
        final Song song = songs.get(i);
        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavor) {
                    if(favoriteMode){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Do you want to Unfavorite the song \n"+song.getSongTitle()+" ?")
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        songs.remove(song);
                                        adapterPresenter.unFavoriteSong(song);
                                        notifyDataSetChanged();
                                    }
                                }).setNegativeButton("NO",null);
                        builder.show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Do you want to Unfavorite the song \n"+song.getSongTitle()+" ?")
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        adapterPresenter.unFavoriteSong(song);
                                        viewHolder.favorite.setImageResource(R.drawable.unfavoritebutton);
                                    }
                                }).setNegativeButton("NO",null);
                        builder.show();
                    }
                }else{
                    adapterPresenter.setFavoriteSong(song);
                    viewHolder.favorite.setImageResource(R.drawable.favoritebutton);
                }
            }
        });
        return view;
    }

    public class ListSongViewHolder{
        public TextView title;
        public TextView artist;
        public ImageView favorite;
    }
}
