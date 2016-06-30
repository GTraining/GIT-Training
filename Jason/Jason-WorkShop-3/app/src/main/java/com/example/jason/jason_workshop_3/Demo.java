package com.example.jason.jason_workshop_3;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Demo extends AppCompatActivity {

    private static final String PREF_NAME = "demo";
    private static final String TEXT_DEMO = "text_demo";
    private static final String TEXT_SAVE = "text_save";
    private SharedPreferences sharedPreferences = null;
    private EditText mEditText = null;
    Button mButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button_save);
        Init();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }
    protected boolean isAuthenticated() {
        return sharedPreferences.getBoolean(TEXT_SAVE, false);
    }

    public void save(){
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(TEXT_DEMO, mEditText.getText().toString());
        e.putBoolean(TEXT_SAVE, true);
        e.commit();
    }

    public void Init(){
        sharedPreferences = getSharedPreferences(PREF_NAME, 0);
        if (isAuthenticated()){
            mEditText.setText(sharedPreferences.getString(TEXT_DEMO, ""));
        }
    }
}
