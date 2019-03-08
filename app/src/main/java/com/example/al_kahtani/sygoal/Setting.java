package com.example.al_kahtani.sygoal;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.al_kahtani.sygoal.classes.AlarmReceiver;
import com.example.al_kahtani.sygoal.classes.SharedPref;

public class Setting extends AppCompatActivity {
    SharedPref sharedpref;
    private Switch daynight,notyoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// select any mode day/night and save it in sharedpref
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }else{  setTheme(R.style.AppTheme);}


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        notyoff=(Switch)findViewById(R.id.Switch1);
        daynight=(Switch)findViewById(R.id.Switch);
        if (sharedpref.loadNightModeState()==true) {
            daynight.setChecked(true);


        }
        // select any mode day/night and save it in sharedpref
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
        //to stop any notification path
        notyoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NotificationManager  notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(1);

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
