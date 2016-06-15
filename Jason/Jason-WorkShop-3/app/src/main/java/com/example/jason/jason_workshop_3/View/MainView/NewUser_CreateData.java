package com.example.jason.jason_workshop_3.View.MainView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.jason.jason_workshop_3.Model.UserModel.Entity.UserBMI;
import com.example.jason.jason_workshop_3.Presenter.PresentMain.PresentCheckUserHealth;
import com.example.jason.jason_workshop_3.R;

/**
 * @author jason
 * Create new data of User's health
 */
public class NewUser_CreateData extends AppCompatActivity {

    private EditText edt_age, edt_weight, edt_height;
    private String UserName = "", _age = "", _height = "", _weight = "";
    private PresentCheckUserHealth mPresentCheckUserHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        UserName = getIntent().getStringExtra("Username");
        edt_age = (EditText) findViewById(R.id.editText_age);
        edt_weight = (EditText) findViewById(R.id.editText_weight);
        edt_height = (EditText) findViewById(R.id.editText_height);
        mPresentCheckUserHealth = new PresentCheckUserHealth(this,UserName);
    }


    public UserBMI getUserHealth(){
        UserBMI userBMI = null;
        userBMI = new UserBMI(UserName, _height, _weight, "14 - 6 - 2016");
        return userBMI;
    }

    public void startImproveYourHealth(View v){
        mPresentCheckUserHealth.startImproveHealth();
    }

    public void checkBMI(View v){
        _height = edt_height.getText().toString();
        _weight = edt_weight.getText().toString();
        _age = edt_age.getText().toString();
        if (_height.equals("") || _weight.equals("") || _age.equals(""))
            Toast.makeText(getApplicationContext(), "Something are empty!", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(NewUser_CreateData.this, "Fuck", Toast.LENGTH_SHORT).show();
            mPresentCheckUserHealth.show(1, Gravity.TOP);
        }
    }

    public void onclickCloseDialog(View v){
        resetEditText();
        mPresentCheckUserHealth.dismissDialog();
    }

    public void onclickCloseActivity(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
    }

    public void resetEditText(){
        edt_age.setText("");
        edt_weight.setText("");
        edt_height.setText("");
    }
}
