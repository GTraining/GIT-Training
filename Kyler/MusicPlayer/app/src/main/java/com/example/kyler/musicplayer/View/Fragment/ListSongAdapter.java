package com.example.kyler.musicplayer.View.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by kyler on 15/06/2016.
 */
public class ListSongAdapter extends BaseAdapter {
    Context context;
    ArrayList<Song> songs;

    public ListSongAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
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
        ListSongViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_list_song_item,null);
            viewHolder = new ListSongViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.list_song_item_title);
            viewHolder.artist = (TextView) view.findViewById(R.id.list_song_item_artist);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ListSongViewHolder) view.getTag();
        }
        viewHolder.title.setText(songs.get(i).getSongTitle());
        viewHolder.artist.setText(songs.get(i).getSongDuration()+"");
        return view;
    }

    public class ListSongViewHolder{
        public TextView title;
        public TextView artist;
    }
}
