package com.example.jason.jason_workshop_3.View.MessageDialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Presenter.Presenter_Feature_Main.DialogMessagaImpl;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PUserManagement;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.DailyDietActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 14/06/2016.
 */
public class CheckCaloDialog implements DialogMessagaImpl {
    private DialogPlus dialog;
    private List<UserBMI> userBMIList;
    private DailyDietActivity mView;
    private PUserManagement mUsermanagement;
    private CurrentLogin mCurrentLogin;
    private UserBMIDatabase mUserBMIDatabase;
    double dSex = 0, dLevelofexercise = 0, dWeightChange = 0, age = 0, height = 0, weight = 0;

    public CheckCaloDialog(DailyDietActivity mView) {
        this.mView = mView;
        mUserBMIDatabase = new UserBMIDatabase(mView);
        mUserBMIDatabase.open();
        mUsermanagement = new PUserManagement(mView);
        mCurrentLogin =  mUsermanagement.checkCurrentLogin();
        userBMIList = mUserBMIDatabase.GETLIST(mCurrentLogin.getUSERNAME());
        age = Double.parseDouble(userBMIList.get(userBMIList.size()-1).getAGE());
        height = userBMIList.get(userBMIList.size()-1).getHEIGHT();
        weight = userBMIList.get(userBMIList.size()-1).getWEIGHT();
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.check_calo_dialog);
                break;
            default:
                holder = new GridHolder(1);
        }
        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
            }
        };
        DialogHandle(holder, gravity, dismissListener);
    }
    @Override
    public void DialogHandle(Holder holder, int gravity, OnDismissListener dismissListener) {
        dialog = DialogPlus.newDialog(mView)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setOnDismissListener(dismissListener)
                .setCancelable(true)
                .create();
        Spinner spinner_sex = (Spinner) dialog.findViewById(R.id.spinner_sex);
        Spinner spinner_lvofexercise = (Spinner) dialog.findViewById(R.id.spinner_levelofexercise);
        Spinner spinner_change = (Spinner) dialog.findViewById(R.id.spinner_whatschange);
        final TextView tvCalo = (TextView) dialog.findViewById(R.id.textView_calo);

        setupSpinner(spinner_sex, setSexData());
        setupSpinner(spinner_lvofexercise, setLevelofExerciseData());
        setupSpinner(spinner_change, setChangeData());

        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dSex = CheckSEX(position);
                tvCalo.setText(String.format("%.0f calo",dSex * dLevelofexercise + dWeightChange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner_lvofexercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dLevelofexercise = CheckLevelofExercise(position);
                tvCalo.setText(String.format("%.0f calories",dSex * dLevelofexercise + dWeightChange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_change.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dWeightChange = CheckWeightChange(position);
                tvCalo.setText(String.format("%.0f calories", dSex * dLevelofexercise + dWeightChange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.show();
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    public List<String> setSexData(){
        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        return list;
    }
    public List<String> setLevelofExerciseData(){
        List<String> list = new ArrayList<>();
        list.add("Little/Not");
        list.add("Light(1-3/week)");
        list.add("Medium(3-5/week)");
        list.add("Lot(5-6/week)");
        list.add("Strong(7/week)");
        return list;
    }
    public List<String> setChangeData(){
        List<String> list = new ArrayList<>();
        list.add("Weight gain");
        list.add("Weight reduction");
        list.add("Maintain");
        return list;
    }

    public void setupSpinner(Spinner spinner, List<String> contentList){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mView, android.R.layout.simple_spinner_item, contentList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public double CheckSEX(int position){
        double BMR = 0;
        switch (position){
            case 0 : BMR = ((13.397 * weight) + (4.799 * height) + (5.677 * 23) + 88.362);
                break;
            case 1 : BMR = ((9.247 * weight) + (3.098 * height) + (4.330 * 23) + 447.593);
                break;
        }
        return BMR;
    }

    public double CheckLevelofExercise(int position){
        double TDEE = 0;
        switch (position){
            case 0 : TDEE = 1.2;
                break;
            case 1 : TDEE = 1.375;
                break;
            case 2 : TDEE = 1.55;
                break;
            case 3 : TDEE = 1.725;
                break;
            case 4 : TDEE = 1.9;
                break;
        }
        return TDEE;
    }

    public double CheckWeightChange(int position){
        double Calo = 0;
        switch (position){
            case 0 : Calo = 250;
                break;
            case 1 : Calo = - 350;
                break;
            case 2 : Calo = 0;
                break;
        }
        return Calo;
    }


}
