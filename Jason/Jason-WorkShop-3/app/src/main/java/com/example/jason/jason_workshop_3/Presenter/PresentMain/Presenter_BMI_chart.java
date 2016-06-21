package com.example.jason.jason_workshop_3.Presenter.PresentMain;

import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.BMI_ChartBar_Data;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.UserManagement;
import com.example.jason.jason_workshop_3.View.FeatureView.BMIChartActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 21/06/2016.
 */
public class Presenter_BMI_chart {

    private List<UserBMI> mparentUserBMIs = new ArrayList<>();

    private BarChart barChart;
    private ArrayList<BarEntry> barEntries;
    private BMIChartActivity mView;

    private UserBMIDatabase mUserBMIDatabase;
    private UserManagement mUserManagement;

    private UserCheckCurrentLogin mCurrentLogin;

    public Presenter_BMI_chart(BMIChartActivity mViews, BarChart mBarChart) {
        this.mView = mViews;
        this.barChart = mBarChart;
        mUserBMIDatabase = new UserBMIDatabase(mView);
        mUserManagement = new UserManagement(mView);
        mUserBMIDatabase.open();
        mparentUserBMIs = setdata();
    }

    public List<UserBMI> setdata(){
        List<UserBMI> startUserBMI = new ArrayList<>();
        mCurrentLogin = mUserManagement.checkCurrentLogin();
        startUserBMI = mUserBMIDatabase.GETLIST(mCurrentLogin.getUSERNAME());
        return startUserBMI;
    }

    public List<BMI_ChartBar_Data> getlistBMI(List<UserBMI> parentUserBMIs, int count) throws ParseException {
        List<BMI_ChartBar_Data> bmiChartBarDatas = new ArrayList<>();
        for (int i = 0; i < parentUserBMIs.size(); i += count){
            bmiChartBarDatas.add(new BMI_ChartBar_Data(parentUserBMIs.get(i).getBMI(), parentUserBMIs.get(i).getCHECKTIME()));
        }
        return bmiChartBarDatas;
    }

    public void CreateBarGraph(List<BMI_ChartBar_Data> bmiChartBarDatas){
        barEntries = new ArrayList<>();
        List<String> DateCheck = new ArrayList<>();
        for (int i = 0; i < bmiChartBarDatas.size(); i ++) {
            float BMI = bmiChartBarDatas.get(i).getCollumn();
            String date = bmiChartBarDatas.get(i).getHeader();
            DateCheck.add(date);
            barEntries.add(new BarEntry(BMI, i));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "BMI");
        BarData barData = new BarData(DateCheck, barDataSet);
        barChart.setData(barData);
        barChart.setDescription("BMI Bar Graph");
    }

    public void showBMIchartbar(String event){
        List<BMI_ChartBar_Data> bmiChartBarDatas = new ArrayList<>();
        if (event.equals("MONTHLY")){
            bmiChartBarDatas = getChartbardata(1);
        } else if (event.equals("YEARLY")){
            bmiChartBarDatas = getChartbardata(4);
        } else {
            bmiChartBarDatas = getChartbardata(12);
        }
        CreateBarGraph(bmiChartBarDatas);
    }

    public List<BMI_ChartBar_Data> getChartbardata(int count){

        List<BMI_ChartBar_Data> bmiChartBarDatas = new ArrayList<>();
        try {
            bmiChartBarDatas = getlistBMI(mparentUserBMIs, count);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bmiChartBarDatas;
    }
}
