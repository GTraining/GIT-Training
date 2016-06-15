package com.example.kyler.musicplayer.View.Fragment;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.kyler.musicplayer.Model.MyService;
import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.Presenter.IListSongPresenter;
import com.example.kyler.musicplayer.Presenter.ListSongPresenter;
import com.example.kyler.musicplayer.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListSongFragment extends ListFragment implements AdapterView.OnItemClickListener, IListSongView{

    IListSongPresenter listSongPresenter;
    ArrayList<Song> songs;

    public ListSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_song,container,false);
        listSongPresenter = new ListSongPresenter(getActivity().getApplicationContext(),this);
        listSongPresenter.getSong();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listSongPresenter.playSong(songs.get(i).getSongPath());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getActivity(),MyService.class);
        getActivity().stopService(intent);
    }

    @Override
    public void loadSong(ArrayList<Song> songs) {
        this.songs = songs;
        ListSongAdapter adapter = new ListSongAdapter(getActivity().getApplicationContext(),songs);
        this.setListAdapter(adapter);
    }
}
