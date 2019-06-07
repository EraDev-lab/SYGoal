package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.al_kahtani.sygoal.classes.SharedPref;

import java.util.Locale;

public class AboutActivity extends AppCompatActivity {
    SharedPref sharedpref;
    ImageView facepic, tweetpic, emailpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);//load night mode setting
        if (sharedpref.loadNightModeState() == true) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        loadLocale();//load languge setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        if (getSupportActionBar() != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
        facepic = findViewById(R.id.fb_logo);
        tweetpic = findViewById(R.id.twitter_logo);
        emailpic = findViewById(R.id.mail_logo);

        emailpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:SYGoal@gmail.com"));  // only email apps should handle this
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        facepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, WebActivity.class);
                intent.putExtra("site", "https://www.facebook.com/");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        tweetpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, WebActivity.class);
                intent.putExtra("site", "https://twitter.com/");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

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
    public boolean onSupportNavigateUp() {
        Intent i = new Intent(getApplicationContext(), BottomNavigationViewActivity.class);
        startActivity(i);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), BottomNavigationViewActivity.class);
        startActivity(i);
        finish();
    }
}
