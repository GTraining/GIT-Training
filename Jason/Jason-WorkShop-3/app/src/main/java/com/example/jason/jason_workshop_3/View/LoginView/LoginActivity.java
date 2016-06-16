package com.example.jason.jason_workshop_3.View.LoginView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.example.jason.jason_workshop_3.Presenter.PresentLogin.PresentUserRegister.UserRegisterDialog;
import com.example.jason.jason_workshop_3.Presenter.PresentLogin.Login;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.UserCheckBMIActivity;
/**
 * @author jason
 * Login function
 */
public class LoginActivity extends AppCompatActivity implements LoginViewImpl {

    private EditText editText_username, editText_password;
    private Login mLoginAdapter;
    private UserRegisterDialog mUserRegisterDialog;
    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_username = (EditText) findViewById(R.id.editText_username);
        mUserRegisterDialog = new UserRegisterDialog(mContext);
        mLoginAdapter = new Login(LoginActivity.this);
    }

    @Override
    public String getUserName() {
        return editText_username.getText().toString();
    }

    @Override
    public String getPassword() {
        return editText_password.getText().toString();
    }

    @Override
    public void onClickCheckUser(View view) {
        mLoginAdapter.onClickCheckUser();
    }

    @Override
    public void onClickUserRegister(View view) {
        resetEditText();
        mUserRegisterDialog.show(1, Gravity.CENTER);
    }

    @Override
    public void onclickSignUp(View v) {
        mUserRegisterDialog.SignUp();
    }

    @Override
    public void onclickCloseDialog(View v) {
        resetEditText();
        mUserRegisterDialog.dismissDialog();
    }

    @Override
    public void OpenMainActivity() {
        Intent mIntent = new Intent(LoginActivity.this, UserMainActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void OpenNewUserActivity() {
        Intent mIntent = new Intent(LoginActivity.this, UserCheckBMIActivity.class);
        mIntent.putExtra("Username",getUserName());
        startActivity(mIntent);
    }

    public void resetEditText(){
        editText_username.setText("");
        editText_password.setText("");
    }

}
