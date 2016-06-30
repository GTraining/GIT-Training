package com.example.jason.jason_workshop_3.View.LoginView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.jason.jason_workshop_3.Application.MyApplication;
import com.example.jason.jason_workshop_3.Presenter.Presenter_LogIn_SignUp.PLogin;
import com.example.jason.jason_workshop_3.R;
import com.example.jason.jason_workshop_3.View.FeatureView.MonthlyCheckBMIActivity;
import com.example.jason.jason_workshop_3.View.UserMainView.UserMainActivity;
/**
 * @author jason
 * Login function
 */
public class LoginActivity extends AppCompatActivity implements LoginViewImpl {

    private EditText editText_username, editText_password;
    private PLogin mLoginAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_username = (EditText) findViewById(R.id.editText_username);
        mLoginAdapter = new PLogin(LoginActivity.this);
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
        startActivities(SignupActivity.class, "none");
    }

    @Override
    public void OpenMainActivity() {
        startActivities(UserMainActivity.class, "none");
    }

    @Override
    public void OpenNewUserActivity() {
        startActivities(MonthlyCheckBMIActivity.class, "1");
    }

    @Override
    public void resetEditText(){
        editText_username.setText("");
        editText_password.setText("");
    }
    @Override
    public void startActivities(Class mClass, String intent){
        Intent mIntent = new Intent(LoginActivity.this, mClass);
        mIntent.putExtra("Intent", intent);
        startActivity(mIntent);
    }
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    public void onclickTwitterLogin(View v){
        startActivities(TwitterLoginActivity.class, "login");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Login Screen");
    }
}
