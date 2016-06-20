package com.example.kyler.musicplayer.View.Fragment;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.kyler.musicplayer.Model.Song;
import com.example.kyler.musicplayer.Presenter.IListSongPresenter;
import com.example.kyler.musicplayer.Presenter.ListSongPresenter;
import com.example.kyler.musicplayer.R;
import com.example.kyler.musicplayer.View.SongDetailActivity;

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
        Intent intent = new Intent(getActivity(), SongDetailActivity.class);
        ArrayList<String> arrSongPaths = new ArrayList<>();
        for(int j=0;j<songs.size();j++){
            arrSongPaths.add(songs.get(j).getSongPath());
        }
        intent.putStringArrayListExtra(String.valueOf(R.string.path),arrSongPaths);
        intent.putExtra(String.valueOf(R.string.currentID),i);
        Log.e("CurrentID",i+" "+songs.get(i).getSongTitle());
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadSong(ArrayList<Song> songs) {
        this.songs = songs;
        ListSongAdapter adapter = new ListSongAdapter(getActivity().getApplicationContext(),songs);
        this.setListAdapter(adapter);
    }
}
