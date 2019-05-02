package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.al_kahtani.sygoal.classes.SharedPref;

import java.util.Locale;

public class Home_Screen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
  Thread thread=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(4000);
                    android.content.Intent russplash=new android.content.Intent(getApplicationContext(),BottomNavigationViewActivity.class);
                    startActivities(new android.content.Intent[]{russplash});
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}};
        thread.start();
        }
        }