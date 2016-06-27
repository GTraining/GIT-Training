package com.example.jason.jason_workshop_3.View.SettingFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jason.jason_workshop_3.R;

/**
 * Created by jason on 25/06/2016.
 */
public class Calendar_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.calendar_fragment, null);
        return view;
    }
}