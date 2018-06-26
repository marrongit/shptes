package com.example.maguilar.shptes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Switch;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Intent intentLogin = new Intent(this,LoginActivity.class);
        Intent intentMain = new Intent(this,MainActivity.class);

        if(!TextUtils.isEmpty(getSharedPrefsEmail(sharedPreferences))){
            startActivity(intentMain);
        } else {
            startActivity(intentLogin);
        }

        finish();
    }

    public String getSharedPrefsEmail(SharedPreferences sharedPreferences){
        return sharedPreferences.getString("email","");
    }

    public String getSharedPrefsName(SharedPreferences sharedPreferences){
        return sharedPreferences.getString("pass","");
    }
}
