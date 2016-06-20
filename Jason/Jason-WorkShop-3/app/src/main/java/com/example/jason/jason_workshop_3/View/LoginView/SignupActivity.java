package com.example.jason.jason_workshop_3.View.LoginView;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jason.jason_workshop_3.Presenter.PresentLogin.Presenter_Signup;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.CheckBMIActivity;

import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity implements SignUpViewImpl{

    private EditText editText_username, editText_password, editText_cfpassword;
    private String Username, password, cf_password;
    private Presenter_Signup mPresenter_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_cfpassword = (EditText) findViewById(R.id.editText_cfpassword);
        mPresenter_signup = new Presenter_Signup(this);
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
        doOpenNewActivity(LoginActivity.class);
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
        Intent mIntent = new Intent(SignupActivity.this, CheckBMIActivity.class);
        mIntent.putExtra("Intent", "1");
        startActivity(mIntent);
    }

    //open new new activity
    @Override
    public void doOpenNewActivity(Class mClass){
        Intent intent = new Intent(SignupActivity.this, mClass);
        startActivity(intent);
    }
}
