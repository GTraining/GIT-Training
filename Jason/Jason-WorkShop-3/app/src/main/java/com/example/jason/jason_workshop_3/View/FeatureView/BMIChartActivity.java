package com.example.jason.jason_workshop_3.View.FeatureView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jason.jason_workshop_3.Presenter.PresentMain.Presenter_BMI_chart;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
import com.github.mikephil.charting.charts.BarChart;

public class BMIChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private Presenter_BMI_chart presenterBmiChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_statistic_bmichart);
        barChart = (BarChart) findViewById(R.id.bargraph);
        presenterBmiChart = new Presenter_BMI_chart(this, barChart);

        presenterBmiChart.showBMIchartbar("MONTHLY");
    }
    public void onclickBack(View v){
        Intent mIntent = new Intent(this, UserMainActivity.class);
        startActivity(mIntent);
    }
    public void onclickMonthlyChart(View v){
        presenterBmiChart.showBMIchartbar("MONTHLY");
    }
    public void onclickYearlyChart(View v){
        presenterBmiChart.showBMIchartbar("YEARLY");
    }
}
