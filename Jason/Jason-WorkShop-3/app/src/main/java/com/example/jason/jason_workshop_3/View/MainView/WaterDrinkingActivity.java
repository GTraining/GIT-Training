package com.example.jason.jason_workshop_3.View.MainView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jason.jason_workshop_3.Presenter.PresentMain.WaterRecyclerViewAdapter;
import com.example.jason.jason_workshop_3.R;

public class WaterDrinkingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private WaterRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_drinking);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 10));
        mAdapter = new WaterRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
