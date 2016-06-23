package com.example.jason.jason_workshop_3.View.MessageDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.jason.jason_workshop_3.Model.UserModel.Entity.CurrentLogin;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.Presenter_UserManagement;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;

/**
 * Created by jason on 16/06/2016.
 */
public class LogoutAlertDialog {
    private UserMainActivity mView;
    private Presenter_UserManagement mUserMangement;
    private CurrentLogin mCurrentLogin;
    public LogoutAlertDialog(UserMainActivity mView) {
        this.mView = mView;
        mUserMangement = new Presenter_UserManagement(mView);
        mCurrentLogin = mUserMangement.checkCurrentLogin();
    }

    public void show(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mView);

//      Fix Bug ID: JS_012 - (Title should be “Warning” and content should be “Do you want to log out?”)

        alertDialogBuilder.setTitle("Warning!");
        alertDialogBuilder
                .setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id) {
                        mUserMangement.UpdateLoginStatus(mCurrentLogin.getID(), "off");
                        mUserMangement.closeDatabase();
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
