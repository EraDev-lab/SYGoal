package com.example.al_kahtani.sygoal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.al_kahtani.sygoal.classes.SharedPref;

public class Setting extends AppCompatActivity {
    SharedPref sharedpref;
    private Switch daynight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }else{  setTheme(R.style.AppTheme);}


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);



        daynight=(Switch)findViewById(R.id.Switch);
        if (sharedpref.loadNightModeState()==true) {
            daynight.setChecked(true);


        }
        daynight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedpref.setNightModeState(true);
                    restartApp();
                }
                else {
                    sharedpref.setNightModeState(false);
                    restartApp();
                }
            }
        });

    }

    public void restartApp () {
        Intent i = new Intent(getApplicationContext(),Setting.class);
        startActivity(i);
        finish();
    }

}
