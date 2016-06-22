package com.example.jason.jason_workshop_3.Presenter.PresentMain;

import android.util.Log;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        mCurrentLogin = mUserManagement.checkCurrentLogin();
        mparentUserBMIs = mUserBMIDatabase.GETLIST(mCurrentLogin.getUSERNAME());
    }
    public List<BMI_ChartBar_Data> getlistBMI(List<UserBMI> parentUserBMIs, String stEvent) throws ParseException {
        boolean check = true;
        int i = 0;
        int count = 0;
        List<BMI_ChartBar_Data> bmiChartBarDatas = new ArrayList<>();
        do {
            for (int j = i + 1; j < parentUserBMIs.size(); j ++) {
                if (!CheckMonth(parentUserBMIs.get(i).getCHECKTIME(), parentUserBMIs.get(j).getCHECKTIME(), stEvent)) {
                    bmiChartBarDatas.add(new BMI_ChartBar_Data(parentUserBMIs.get(i).getBMI(), parentUserBMIs.get(i).getCHECKTIME()));
                    count ++;
                    break;
                }
                Log.e("For", "" + j);
                count ++;
            }
            i = count;
        }while (i != (parentUserBMIs.size() -1));
        return bmiChartBarDatas;
    }

    public boolean CheckMonth(String date1, String date2, String stEvent){
        boolean check = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar mCalendar1 = Calendar.getInstance();
        Calendar mCalendar2 = Calendar.getInstance();
        Date mdate1 = null, mdate2 = null;
        try {
            mdate1 = dateFormat.parse(date1);
            mdate2 = dateFormat.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mCalendar1.setTime(mdate1);
        mCalendar2.setTime(mdate2);
        int month1 = mCalendar1.get(Calendar.MONTH);
        int year1 = mCalendar1.get(Calendar.YEAR);
        int year2 = mCalendar2.get(Calendar.YEAR);
        int month2 = mCalendar2.get(Calendar.MONTH);
        if (stEvent.equals("MONTHLY")) {
            if (year1 == year2 && month1 == month2) check = true;
            else check = false;
        }
        else{
            if (year1 == year2) check = true;
            else check = false;
        }
        return check;
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

    public void showBMIchartbar(String stEvent){
        List<BMI_ChartBar_Data> bmiChartBarDatas = new ArrayList<>();
            bmiChartBarDatas = getChartbardata(stEvent);
        CreateBarGraph(bmiChartBarDatas);
    }

    public List<BMI_ChartBar_Data> getChartbardata(String  stEvent){

        List<BMI_ChartBar_Data> bmiChartBarDatas = new ArrayList<>();
        try {
            bmiChartBarDatas = getlistBMI(mparentUserBMIs, stEvent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bmiChartBarDatas;
    }
}
