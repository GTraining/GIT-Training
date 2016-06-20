package com.example.jason.jason_workshop_3.View.MessageDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.UserManagement;
import com.example.jason.jason_workshop_3.View.FeatureView.CheckBMIActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

/**
 * Created by jason on 16/06/2016.
 */
public class CheckBMIAlertDialog {
    private CheckBMIActivity mView;
    public CheckBMIAlertDialog(CheckBMIActivity mView) {
        this.mView = mView;
    }

    public void show(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mView);
        alertDialogBuilder.setTitle("Warning!");
        alertDialogBuilder
                .setMessage("Weight(min-max): 1-500(kg) \n" +
                        "Height(min-max): 10-300(cm)")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}