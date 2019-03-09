package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.al_kahtani.sygoal.classes.SharedPref;

import java.util.Locale;

public class Setting extends AppCompatActivity {
    SharedPref sharedpref;
    private Switch daynight,notyoff;
    private ImageView langsetting;
    LinearLayout about, rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// select any mode day/night and save it in sharedpref
        sharedpref = new SharedPref(this);//load night mode setting


        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }else{  setTheme(R.style.AppTheme);}
        loadLocale();// to  load selected language

        super.onCreate(savedInstanceState);
        loadLocale();//load languge setting
        setContentView(R.layout.activity_setting);

        about=findViewById(R.id.about);
        rate=findViewById(R.id.rate);
        langsetting=findViewById(R.id.Language_setting);
        notyoff=(Switch)findViewById(R.id.Switch1);
        daynight=(Switch)findViewById(R.id.Switch);
        // to keep night mode switch state
        if (sharedpref.loadNightModeState()==true) {
            daynight.setChecked(true);
        }
        // to keep notification switch state. keep it in SharedPreferences
        SharedPreferences pref11 = getSharedPreferences("sharedprefnoty", Activity.MODE_PRIVATE);
        boolean notifys = pref11.getBoolean("notifymode", false);
        if (notifys==true) {
            notyoff.setChecked(true);
            // unactivate notification
            NotificationManager  notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(1);
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
        // select any mode notification in on/off and save it in sharedpref
        notyoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor1 = getSharedPreferences("sharedprefnoty", Context.MODE_PRIVATE).edit();
                if (isChecked) {
                    editor1.putBoolean("notifymode", true);
                    editor1.apply();

                    NotificationManager  notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(1);
                }
                else {
                    editor1.putBoolean("notifymode", false);
                    editor1.apply();

                    // motwakel, for loop for activate notification will be here.
                }
            }
        });
        //to stop any notification path
     /**   notyoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NotificationManager  notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(1);

                }

            }
        });**/

     // to select languge
        langsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Setting.this, AboutActivity.class);
                startActivity(it);
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent rateIntent = rateIntentForUrl("market://details");
                    startActivity(rateIntent);
                } catch (ActivityNotFoundException e) {
                    Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
                    startActivity(rateIntent);
                }            }
        });

    }
    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
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
    public void restartApp () {
        Intent i = new Intent(getApplicationContext(),Setting.class);
        startActivity(i);
        finish();
    }

    private void showChangeLanguageDialog() {
        SharedPreferences pref = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        int position = pref.getInt("position", -1);
        final String[] listItme = {"English", "العربية"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Setting.this);
        mBuilder.setTitle(R.string.choose_anguage);
        mBuilder.setIcon(R.drawable.ic_settings_lang);
        mBuilder.setSingleChoiceItems(listItme, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("en", i);
                    recreate();
                } else if (i == 1) {
                    setLocale("ar", i);
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();


        mDialog.show();
    }
    // languge setting
    public void setLocale(String lang, int pos) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit();// keep language in SharedPreferences
        editor.putString("My_Lang", lang);
        editor.putInt("position", pos);
        editor.apply();

    }
// pull language from SharedPreferences
    public void loadLocale() {
        SharedPreferences pref = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        int position = pref.getInt("position", -1);
        setLocale(language,position);
    }

}
