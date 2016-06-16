package com.example.jason.jason_workshop_3.Presenter.PresentLogin;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckCurrentLogin;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

/**
 * Created by jason on 16/06/2016.
 */
public class LogoutAlertDialog {
    private UserMainActivity mView;
    private UserManagement mUserMangement;
    private UserCheckCurrentLogin mCurrentLogin;
    public LogoutAlertDialog(UserMainActivity mView) {
        this.mView = mView;
        mUserMangement = new UserManagement(mView);
        mCurrentLogin = mUserMangement.checkCurrentLogin();
    }

    public void show(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mView);
        alertDialogBuilder.setTitle("Log Out!");
        alertDialogBuilder
                .setMessage("Do you want to log out!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id) {
                        mUserMangement.UpdateLogin(mCurrentLogin.getID(), "off");
                        mView.LogoutIntent();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
