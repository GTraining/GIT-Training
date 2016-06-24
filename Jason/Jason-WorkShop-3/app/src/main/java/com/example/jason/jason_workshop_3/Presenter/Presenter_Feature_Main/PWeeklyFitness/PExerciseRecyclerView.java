package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.WeeklyFitnessActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jason on 24/06/2016.
 */
public class PExerciseRecyclerView extends RecyclerView.Adapter<MExerciseViewholder> {

    private WeeklyFitnessActivity mView;
    private MExerciseViewholder viewholder;
    private int lastPosition = -1;
    private int dayofweek;

    private List<Exercise> exerciseList = new ArrayList<>();

    public PExerciseRecyclerView(WeeklyFitnessActivity mView, int dayofweek) {
        this.mView = mView;
        this.dayofweek = dayofweek;
        exerciseList = JsonHandler(String.valueOf(dayofweek));

    }

    @Override
    public MExerciseViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mView).inflate(R.layout.fitness_workout_item, null);
        viewholder = new MExerciseViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(MExerciseViewholder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.tvExercise_number.setText(exercise.getId());
        holder.tvExercise_name.setText(exercise.getName());
        holder.tvAmountOfStep.setText(exercise.getWeigh());
        holder.tvWeigh.setText(exercise.getId());
        if (position > 1) {
            holder.lWorkoutBlend.setBackgroundResource(R.color.blend_black_99);
        }
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mView, R.anim.slide_in_top);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mView.getAssets().open("fitness.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public List<Exercise> JsonHandler(String dayofweek){
        List<Exercise> exerciseList = new ArrayList<>();
        try {
            JSONObject wJsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray wJsonArray = null;
            if (wJsonObject != null) {
                wJsonArray = wJsonObject.getJSONArray("Workout");
                for (int i = 0; i < wJsonArray.length(); i++) {
                    JSONObject jo_inside = wJsonArray.getJSONObject(i);
                    String wID = jo_inside.getString("id");

                    if (wID.equals(dayofweek)) {
                        //List exercise
                        JSONArray eJsonArray = jo_inside.getJSONArray("excercise");
                        for (int j = 0; j < eJsonArray.length(); j++) {
                            JSONObject eJsonObject = eJsonArray.getJSONObject(j);
                            String eID = eJsonObject.getString("id");
                            String eName = eJsonObject.getString("name");
                            String eAmountofStep = eJsonObject.getString("amountofstep");
                            String eWeigh = eJsonObject.getString("weigh");
                            exerciseList.add(new Exercise(eID, eName, eAmountofStep, eWeigh));
                        }
                        break;
                    }

                }
            }
            else Toast.makeText(mView, "Null", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exerciseList;
    }
}
