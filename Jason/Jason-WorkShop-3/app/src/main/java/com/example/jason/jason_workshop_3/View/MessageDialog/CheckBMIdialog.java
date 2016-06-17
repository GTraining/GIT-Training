package com.example.jason.jason_workshop_3.View.MessageDialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.Model.ClockModel.ClockDate;
import com.example.jason.jason_workshop_3.Model.ClockModel.CurrentDate;
import com.example.jason.jason_workshop_3.Model.UserModel.Data.UserBMIDatabase;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.UserManagement;
import com.example.jason.jason_workshop_3.Presenter.PresentMain.DialogMessagaImpl;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.LoginView.LoginActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

/**
 * Created by jason on 16/06/2016.
 */
public class CheckBMIdialog implements DialogMessagaImpl {

    private EditText edt_age, edt_weight, edt_height;
    private Button btn_check;
    private String _age = "", _height = "", _weight = "";
    private DialogPlus dialog;
    private UserMainActivity mView;
    private UserManagement userManagement;
    private UserBMIDatabase mUserBMIDatabase;
    private UserBMI mUserBMI;
    private UserCheckCurrentLogin mCurrentLogin;
    private CurrentDate mCurrentDate = new CurrentDate();
    private ClockDate mClockDate;

    public CheckBMIdialog(UserMainActivity mainActivity) {
        this.mView = mainActivity;
        userManagement = new UserManagement(mView);
        mUserBMIDatabase = new UserBMIDatabase(mView);
        mUserBMIDatabase.open();
        mClockDate = mCurrentDate.getmClockDate();
        mCurrentLogin = userManagement.checkCurrentLogin();
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.check_bmi_dialog);
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

        edt_age = (EditText) dialog.findViewById(R.id.editText_age);
        edt_weight = (EditText) dialog.findViewById(R.id.editText_weight);
        edt_height = (EditText) dialog.findViewById(R.id.editText_height);
        btn_check = (Button) dialog.findViewById(R.id.button_chcekBMI);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBMI();
            }
        });
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }
    public void checkBMI(){
        _height = edt_height.getText().toString();
        _weight = edt_weight.getText().toString();
        _age = edt_age.getText().toString();
        if (_height.equals("") || _weight.equals("") || _age.equals(""))
            Toast.makeText(mView, "Something are empty!", Toast.LENGTH_LONG).show();
        else {
            String date = mClockDate.getDay() + "-" + mClockDate.getMonth() + "-" + mClockDate.getYear();
            mUserBMI = new UserBMI(mCurrentLogin.getUSERNAME(), _height, _weight, date);
            mUserBMIDatabase.INSERT(mUserBMI);
            Toast.makeText(mView, "BMI : " + mUserBMI.getBMI() + "\n"
                    + mUserBMI.convertBMI(mUserBMI.getBMI()), Toast.LENGTH_SHORT).show();
//            mUserBMIDatabase.close();
//            userManagement.closeDatabase();
            dismissDialog();
        }
    }
}
