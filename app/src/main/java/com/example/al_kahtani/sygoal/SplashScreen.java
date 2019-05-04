package com.example.al_kahtani.sygoal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent=new Intent(SplashScreen.this,BottomNavigationViewActivity.class);
                    startActivity(intent);
                }
            }
        }) ;
        timer.start();
    }
}
