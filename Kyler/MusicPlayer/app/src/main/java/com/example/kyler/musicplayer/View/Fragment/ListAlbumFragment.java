package com.example.kyler.musicplayer.View.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kyler.musicplayer.Model.Album;
import com.example.kyler.musicplayer.Presenter.IListAlbumPresenter;
import com.example.kyler.musicplayer.Presenter.ListAlbumPresenter;
import com.example.kyler.musicplayer.R;

import java.util.ArrayList;

public class ListAlbumFragment extends Fragment implements IListAlbumView {

    RecyclerView listAlbumRecyclerView;
    ListAlbumAdapter adapter;
    IListAlbumPresenter presenter;

    public ListAlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_album,container,false);
        listAlbumRecyclerView = (RecyclerView) view.findViewById(R.id.list_album_recyclerview);
        listAlbumRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        presenter = new ListAlbumPresenter(getActivity().getApplicationContext(),this);
        presenter.getAlbums();
        return view;
    }

    @Override
    public void loadAlbum(ArrayList<Album> albums) {
        adapter = new ListAlbumAdapter(getActivity(),albums);
        listAlbumRecyclerView.setAdapter(adapter);
    }
}
