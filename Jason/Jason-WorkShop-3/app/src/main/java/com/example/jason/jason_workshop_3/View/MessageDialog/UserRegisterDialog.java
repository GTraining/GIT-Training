package com.example.jason.jason_workshop_3.View.MessageDialog;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.DialogLibrary.DialogPlus;
import com.example.jason.jason_workshop_3.DialogLibrary.GridHolder;
import com.example.jason.jason_workshop_3.DialogLibrary.Holder;
import com.example.jason.jason_workshop_3.DialogLibrary.OnDismissListener;
import com.example.jason.jason_workshop_3.DialogLibrary.ViewHolder;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserCheckInfo;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.User;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.UserManagement;
import com.example.jason.jason_workshop_3.Presenter.PresentMain.DialogMessagaImpl;
import com.example.jason.jason_workshop_3.R;

/**
 * Created by jason on 13/06/2016.
 */
public class UserRegisterDialog implements UserRegisterDialogImpl, DialogMessagaImpl {
    private Context mContext;
    private EditText editText_username, editText_password, editText_cfpassword;
    private String Username, password, cf_password;
    DialogPlus dialog;
    UserManagement mUserMangement;
    public UserRegisterDialog(Context mContext) {
        this.mContext = mContext;
        mUserMangement = new UserManagement(mContext);
    }

    @Override
    public void show(int holderId, int gravity) {
        Holder holder;
        switch (holderId) {
            case 1:
                holder = new ViewHolder(R.layout.user_register_dialog);
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
        dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setOnDismissListener(dismissListener)
                .setCancelable(true)
                .create();
        editText_username = (EditText) dialog.findViewById(R.id.editText_username);
        editText_password = (EditText) dialog.findViewById(R.id.editText_password);
        editText_cfpassword = (EditText) dialog.findViewById(R.id.editText_cfpassword);
        dialog.show();
    }

    @Override
    public void SignUp() {
        Username = editText_username.getText().toString();
        password = editText_password.getText().toString();
        cf_password = editText_cfpassword.getText().toString();
        if (Empty()) {
            Toast.makeText(mContext, "Something is empty!", Toast.LENGTH_LONG).show();
            editText_password.setText("");
            editText_cfpassword.setText("");
        }
        else if (!checkPassword()){
            Toast.makeText(mContext, "Confirm password is incorrect!", Toast.LENGTH_LONG).show();
            editText_password.setText("");
            editText_cfpassword.setText("");
        }
        else {
            UserCheckInfo mCheckLogin = mUserMangement.checkExisting(Username);
            if (!mCheckLogin.isExisted()){
                mUserMangement.createUser(new User(Username, password));
                dialog.dismiss();
            } else {
                Toast.makeText(mContext, "Username is existed!", Toast.LENGTH_LONG).show();
                editText_password.setText("");
                editText_cfpassword.setText("");
                editText_username.setText("");
            }
        }
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    public boolean Empty(){
        boolean check = false;
        if (Username.equals("") || password.equals("") || cf_password.equals(""))
            check = true;
        return check;
    }

    @Override
    public boolean checkPassword(){
        if (password.equals(cf_password)){
            return true;
        }else return false;
    }
}
