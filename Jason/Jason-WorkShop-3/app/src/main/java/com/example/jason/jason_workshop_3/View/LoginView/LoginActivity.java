package com.example.jason.jason_workshop_3.View.LoginView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jason.jason_workshop_3.Presenter.PresentLogin.Presenter_Login;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
import com.example.jason.jason_workshop_3.View.FeatureView.UserCheckBMIActivity;
/**
 * @author jason
 * Login function
 */
public class LoginActivity extends AppCompatActivity implements LoginViewImpl {

    private EditText editText_username, editText_password;
    private Presenter_Login mLoginAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_username = (EditText) findViewById(R.id.editText_username);
        mLoginAdapter = new Presenter_Login(LoginActivity.this);
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
        doOpenNewActivity(Signup.class, "");
    }

    @Override
    public void OpenMainActivity() {
        doOpenNewActivity(UserMainActivity.class, "");
    }

    @Override
    public void OpenNewUserActivity() {
        doOpenNewActivity(UserCheckBMIActivity.class, getUserName());
    }

    @Override
    public void resetEditText(){
        editText_username.setText("");
        editText_password.setText("");
    }
    @Override
    public void doOpenNewActivity(Class mClass, String mIntent){
        Intent intent = new Intent(LoginActivity.this, mClass);
        intent.putExtra("Username",mIntent);
        startActivity(intent);
    }
}
