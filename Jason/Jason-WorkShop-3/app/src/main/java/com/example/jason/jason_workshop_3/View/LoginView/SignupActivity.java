package com.example.jason.jason_workshop_3.View.LoginView;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PSignup;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.MonthlyCheckBMIActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * I've replaced sign up dialog by sign up activity to solve that bug. On Message dialog, Keyboard can focus to editTexts
 * so i think this is a easy way, which i can do.
 */

public class SignupActivity extends AppCompatActivity implements SignUpViewImpl{

    private EditText editText_username, editText_password, editText_cfpassword;
    private String Username, password, cf_password;
    private PSignup mPresenter_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_cfpassword = (EditText) findViewById(R.id.editText_cfpassword);
        mPresenter_signup = new PSignup(this);
    }

    @Override
    public List<String> getSignUpInfor(){
        List<String> list = new ArrayList<>();
        list.add(Username);
        list.add(password);
        list.add(cf_password);
        return list;
    }
    // back to login activity
    @Override
    public void onclickBack(View v){
        startActitivities(LoginActivity.class, "none");
    }

    // Sign up
    @Override
    public void onclickSignUp(View v){
        Username = editText_username.getText().toString();
        password = editText_password.getText().toString();
        cf_password = editText_cfpassword.getText().toString();
        mPresenter_signup.SignUp();
    }

    //set empty edittext
    @Override
    public void UpdateAllEditText(){
        editText_password.setText("");
        editText_cfpassword.setText("");
        editText_username.setText("");
    }

    //set empty edittext
    @Override
    public void UpdatePasswordEditText(){
        editText_password.setText("");
        editText_cfpassword.setText("");
    }

    //open new user activity
    @Override
    public void OpenNewUserActivity() {
        startActitivities(MonthlyCheckBMIActivity.class, "1");
    }

    //open new new activity
    @Override
    public void startActitivities(Class mClass, String intent){
        Intent mIntent = new Intent(SignupActivity.this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }
}
