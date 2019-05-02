package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.al_kahtani.sygoal.classes.SharedPref;

import java.util.Locale;

public class Home_Screen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Thread thread = new Thread(){

            @Override


            public  void  run() {

                try {
                    sleep(3000);

                    Intent intent = new Intent(getApplicationContext(),BottomNavigationViewActivity.class);
                    startActivity(intent);

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
    }
}