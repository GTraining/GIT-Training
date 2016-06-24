package com.example.jason.jason_workshop_3.View.FeatureView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness.PExerciseRecyclerView;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness.PWeeklyRecyclerView;
import com.example.jason.jason_workshop_3.R;

public class WeeklyFitnessActivity extends AppCompatActivity {

    private PExerciseRecyclerView exerciseRecyclerView;
    private PWeeklyRecyclerView weeklyRecyclerView;
    private RecyclerView recyclerView_week;
    private RecyclerView recyclerView_exercise;
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
}
