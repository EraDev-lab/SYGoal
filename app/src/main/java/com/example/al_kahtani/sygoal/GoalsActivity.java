package com.example.al_kahtani.sygoal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.al_kahtani.sygoal.fragments.Acheivement_fragment;
import com.example.al_kahtani.sygoal.fragments.CurrentGoalsFragment;
import com.example.al_kahtani.sygoal.fragments.MissedGoalsFragment;

public class GoalsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container, new CurrentGoalsFragment()).commit();

        fab = (FloatingActionButton)findViewById(R.id.fabm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoalsActivity.this,TaskActivity.class));
            }
        });
    }

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
            break;
            case R.id.nav_missed_goals: selectedFragment = new MissedGoalsFragment();
            break;
            case R.id.nav_achievements: selectedFragment = new Acheivement_fragment();
            break;
        }

        if (selectedFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,selectedFragment).commit();
        }
        return true;
    }
}
