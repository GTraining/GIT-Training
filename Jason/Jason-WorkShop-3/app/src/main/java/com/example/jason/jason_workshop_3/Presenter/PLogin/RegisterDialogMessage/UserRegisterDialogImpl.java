package com.example.jason.jason_workshop_3.Presenter.PLogin.RegisterDialogMessage;

import com.example.jason.jason_workshop_3.Dialog.Holder;
import com.example.jason.jason_workshop_3.Dialog.OnDismissListener;

/**
 * Created by jason on 13/06/2016.
 */
public interface UserRegisterDialogImpl {
    void show(int holderId, int gravity);
    void DialogHandle(Holder holder, int gravity, OnDismissListener dismissListener);
    void SignUp();
    void DismissDialog();
    boolean Empty();
    boolean checkPassword();
}
