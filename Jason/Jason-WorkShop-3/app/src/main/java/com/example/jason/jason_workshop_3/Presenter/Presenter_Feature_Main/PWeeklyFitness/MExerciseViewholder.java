package com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PWeeklyFitness;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jason.jason_workshop_3.R;

/**
 * Created by jason on 24/06/2016.
 */
public class MExerciseViewholder extends RecyclerView.ViewHolder{
    public TextView tvExercise_number;
    public TextView tvExercise_name;
    public TextView tvAmountOfStep;
    public TextView tvWeigh;
    public ImageView imgFinish;
    public RelativeLayout lWorkoutBlend;


    public MExerciseViewholder(View itemView) {
        super(itemView);
        this.tvExercise_name = (TextView) itemView.findViewById(R.id.textView_exerciseName);
        this.tvExercise_number = (TextView) itemView.findViewById(R.id.textView_exercise_number);
        this.tvAmountOfStep = (TextView) itemView.findViewById(R.id.textView_amountofStep);
        this.tvWeigh = (TextView) itemView.findViewById(R.id.textView_weigh);
        this.imgFinish = (ImageView) itemView.findViewById(R.id.imageView_workout_check);
        this.lWorkoutBlend = (RelativeLayout) itemView.findViewById(R.id.layout_workout_blend);
    }
}
