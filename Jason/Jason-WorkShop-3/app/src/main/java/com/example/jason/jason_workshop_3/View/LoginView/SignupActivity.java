package com.example.jason.jason_workshop_3.View.LoginView;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.jason_workshop_3.Application.MyApplication;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PSignup;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.MonthlyCheckBMIActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * I've replaced sign up dialog by sign up activity to solve that bug. On Message dialog, Keyboard can focus to editTexts
 * so i think this is a easy way, which i can do.
 */

public class SignupActivity extends AppCompatActivity implements SignUpViewImpl, View.OnKeyListener{

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
        editText_cfpassword.setOnKeyListener(this);
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
        startActivities(LoginActivity.class, "none");
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
        startActivities(MonthlyCheckBMIActivity.class, "1");
    }

    //open new new activity
    @Override
    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(SignupActivity.this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Sign up Screen");
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            Toast.makeText(SignupActivity.this, "SIGN UP", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.keyboard == Configuration.KEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.keyboard == Configuration.KEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }
}
