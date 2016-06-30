package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jason.jason_workshop_3.Application.HockeyAppManager;
import com.example.jason.jason_workshop_3.Application.MyApplication;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness.PExerciseRecyclerView;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness.PWeeklyRecyclerView;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

public class WeeklyFitnessActivity extends AppCompatActivity {

    private PExerciseRecyclerView exerciseRecyclerView;
    private PWeeklyRecyclerView weeklyRecyclerView;
    private RecyclerView recyclerView_week;
    private RecyclerView recyclerView_exercise;
    private HockeyAppManager hockeyAppManager = new HockeyAppManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_management);
        recyclerView_week = (RecyclerView) findViewById(R.id.recyclerview_week);
        recyclerView_exercise = (RecyclerView) findViewById(R.id.recyclerview_exercise);

        recyclerView_week.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView_exercise.setLayoutManager(new GridLayoutManager(this, 1));

        weeklyRecyclerView = new PWeeklyRecyclerView(this);
        exerciseRecyclerView = new PExerciseRecyclerView(this, weeklyRecyclerView.getDayofweek());

        recyclerView_week.setAdapter(weeklyRecyclerView);
        recyclerView_exercise.setAdapter(exerciseRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hockeyAppManager.checkForCrashes();
        MyApplication.getInstance().trackScreenView("Weekly Fitness Screen");
    }

    public void onclickBack(View v){
        startActivities(UserMainActivity.class, "none");
    }

    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }
}
