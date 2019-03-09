package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.al_kahtani.sygoal.classes.SharedPref;

import java.util.Locale;
public class GoalActivity extends AppCompatActivity {
EditText new_goal;
    private RadioButton radioButton;
    private Button btnSave;

    SharedPref sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);//load night mode setting
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }else{  setTheme(R.style.AppTheme);}
        loadLocale();//load languge setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        new_goal = findViewById(R.id.goal);
        radioButton = findViewById(R.id.radiobutton);
    //    radioGroup = (RadioGroup) findViewById(R.id.radio);
        btnSave = (Button) findViewById(R.id.Button);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
           //     int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
             //   radioButton =  findViewById(selectedId);

                Toast.makeText(GoalActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

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
        SharedPreferences.Editor editor = getSharedPreferences("Setting", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences pref = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }

}
