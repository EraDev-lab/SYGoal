package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.al_kahtani.sygoal.classes.SharedPref;
import com.example.al_kahtani.sygoal.fragments.Achievement_fragment;
import com.example.al_kahtani.sygoal.fragments.CurrentGoalsFragment;
import com.example.al_kahtani.sygoal.fragments.MissedGoalsFragment;

import java.util.Locale;

public class BottomNavigationViewActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    SharedPref sharedpref;
    FloatingActionButton fab;

    String updateGoal = "0";
    int goalActivityNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);//load night mode setting
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }else{  setTheme(R.style.AppTheme);}
        loadLocale();//load languge setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_view);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container, new CurrentGoalsFragment()).commit();

        fab = findViewById(R.id.fabm);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(BottomNavigationViewActivity.this,GoalActivity.class);
                 intent.putExtra("updateGoal", updateGoal);
                 intent.putExtra("goalActivity", goalActivityNumber);
                 startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(BottomNavigationViewActivity.this, SettingActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           finish();
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_home) {

            Intent intent = new Intent(BottomNavigationViewActivity.this, Home_Screen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         finish();
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_contact) {
            Intent intent = new Intent(BottomNavigationViewActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_rate) {
            try {
                Intent rateIntent = rateIntentForUrl("market://details");
                startActivity(rateIntent);
            } catch (ActivityNotFoundException e) {
                Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
                startActivity(rateIntent);
            }
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?goalId=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    } // rate us helper method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home__screen,menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()){
            case R.id.nav_current_goal: selectedFragment = new CurrentGoalsFragment();
            goalActivityNumber = 1;
            break;
            case R.id.nav_missed_goals: selectedFragment = new MissedGoalsFragment();
            goalActivityNumber = 2;
            break;
            case R.id.nav_achievements: selectedFragment = new Achievement_fragment();
            goalActivityNumber = 3;
            break;
        }

        if (selectedFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,selectedFragment).commit();
        }
        return true;
    }
    // languge setting
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("SettingActivity", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences pref = getSharedPreferences("SettingActivity", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
