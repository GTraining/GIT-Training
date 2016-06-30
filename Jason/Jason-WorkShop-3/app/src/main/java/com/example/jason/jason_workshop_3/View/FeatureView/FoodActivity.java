package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jason.jason_workshop_3.Application.MyApplication;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.PDailyDiet.PListFoodAdapter;
import com.example.jason.jason_workshop_3.R;

public class FoodActivity extends AppCompatActivity {

    private PListFoodAdapter pListFood;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        pListFood = new PListFoodAdapter(this);
        recyclerView.setAdapter(pListFood);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("List Food Screen");
    }

    public void onclickBack(View v){
        MyApplication.getInstance().trackEvent("ListFood", "onclick", "On click back");
        Intent mIntent = new Intent(this, DailyDietActivity.class);
        startActivity(mIntent);
    }
}
