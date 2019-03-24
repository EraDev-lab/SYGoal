package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.al_kahtani.sygoal.classes.SharedPref;
import com.example.al_kahtani.sygoal.data.GoalContract;
import com.example.al_kahtani.sygoal.data.HelperClass;
import com.example.al_kahtani.sygoal.fragments.CurrentGoalsFragment;

import java.util.Locale;

public class GoalActivity extends AppCompatActivity {

    EditText goalName, goalDescription;

    RadioGroup radioGroup;

    RadioButton jobRadioButton, houseWorkRadioButton, educationRadioButton, exerciseRadioButton,
    socialRadioButton, otherRadioButton;

    Button saveGoal, deleteGoal;

    int selectedType = 0;
    long goalId;
    int goalActivityNumber;
    String mGoalName;
    String mGoalDescription;
    String updateGoal ="0";
    String updateTask = "0";
    double percentage;
    String maxDate;

    SharedPref sharedpref;
    HelperClass helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);//load night mode setting
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }else{  setTheme(R.style.AppTheme);}
        loadLocale();//load languge setting

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Intent intent = getIntent();

        goalId = intent.getLongExtra("goalId", goalId);
        updateGoal = intent.getStringExtra("updateGoal");
        goalActivityNumber = intent.getIntExtra("goalActivity", goalActivityNumber);

        helper = new HelperClass(this);
        //find EditText by goalId
        goalName = findViewById(R.id.goal_name);
        goalDescription = findViewById(R.id.goal_description);
        //find RadioButton by goalId
        jobRadioButton = findViewById(R.id.job_radiobutton);
        houseWorkRadioButton = findViewById(R.id.housework_radiobutton);
        educationRadioButton = findViewById(R.id.education_radiobutton);
        exerciseRadioButton = findViewById(R.id.exercise_radiobutton);
        socialRadioButton = findViewById(R.id.socail_radiobutton);
        otherRadioButton = findViewById(R.id.other_radiobutton);
        //find Button by goalId
        saveGoal = findViewById(R.id.save_goal);
        deleteGoal = findViewById(R.id.delete_goal);
        radioGroup = findViewById(R.id.radiogroup);

radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (jobRadioButton.isChecked()) {
            selectedType = 1;
        } else if (houseWorkRadioButton.isChecked()) {
            selectedType = 2;
        } else if (educationRadioButton.isChecked()) {
            selectedType = 3;
        } else if (exerciseRadioButton.isChecked()) {
            selectedType = 4;
        } else if (socialRadioButton.isChecked()) {
            selectedType = 5;
        } else if (otherRadioButton.isChecked()) {
            selectedType = 6;
        }
    }
});

        if (updateGoal.equals("1")){
            setTitle("Edit Goal");

            db = helper.getReadableDatabase();

            Cursor cursor = db.query(GoalContract.TABLE_NAME,
                    new String[]{GoalContract._ID,
                            GoalContract.Goal_Name,
                            GoalContract.Goal_Type,
                            GoalContract.Goal_Description},

                    GoalContract._ID + "=?",
                    new String[]{String.valueOf(goalId)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

            // prepare contract object
            goalId =cursor.getInt(cursor.getColumnIndex(GoalContract._ID));
            mGoalName = cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Name));
            selectedType = cursor.getInt(cursor.getColumnIndex(GoalContract.Goal_Type));
            mGoalDescription = cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Description));

            switch(selectedType){
                case 1:
                    radioGroup.check(R.id.job_radiobutton);
                    break;
                case 2:
                    radioGroup.check(R.id.housework_radiobutton);
                    break;
                case 3:
                    radioGroup.check(R.id.education_radiobutton);
                    break;
                case 4:
                    radioGroup.check(R.id.exercise_radiobutton);
                    break;
                case 5:
                    radioGroup.check(R.id.socail_radiobutton);
                    break;
                case 6:
                    radioGroup.check(R.id.other_radiobutton);
                    break;
            }
            // close the db connection
            cursor.close();
            db.close();

            goalName.setText(mGoalName);
            goalDescription.setText(mGoalDescription);
        }

        else if (updateGoal.equals("0")){
            setTitle("New Goal");
            deleteGoal.setVisibility(View.GONE);
        }

        saveGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mGoalName = goalName.getText().toString();
                mGoalDescription = goalDescription.getText().toString();
                // get selected radio button from radioGroup
           //     int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned goalId
             //   jobRadioButton =  findViewById(selectedId);

                //Toast.makeText(GoalActivity.this, jobRadioButton.getText(), Toast.LENGTH_SHORT).show();
                if (mGoalName.isEmpty() || mGoalDescription.isEmpty()) {
                    if (mGoalName.isEmpty()) {
                        goalName.setError(getString(R.string.goal_is_required));
                        goalName.requestFocus();
                    }

                    if (mGoalDescription.isEmpty()) {
                        goalDescription.setError(getString(R.string.description_is_required));
                        goalDescription.requestFocus();
                    }
                    return;
                }
                else if (updateGoal.equals("0")){
                    Intent intent = new Intent(GoalActivity.this, TaskActivity.class);
                    updateGoal ="0";
                    percentage = 00.0;
                    maxDate = "2019-3-17";

                    goalId = helper.insertGoal(mGoalName, selectedType, mGoalDescription, maxDate, percentage, goalActivityNumber);

                    intent.putExtra("goalId", goalId);
                    //intent.putExtra("updateGoal", updateGoal);
                    intent.putExtra("updateTask", updateTask);
                    startActivity(intent);
                }
                else if (updateGoal.equals("1")){
                    Intent intent = new Intent(GoalActivity.this, BottomNavigationViewActivity.class);
                    helper.updateGoal(goalId, mGoalName, selectedType, mGoalDescription,goalActivityNumber);
                    updateGoal ="1";
                    updateTask = "0";
                    intent.putExtra("goalId", goalId);
                    intent.putExtra("updateGoal", updateGoal);
                    intent.putExtra("updateTask", updateTask);
                    startActivity(intent);
                }
            }

        });

        deleteGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalActivity.this, BottomNavigationViewActivity.class);
                helper.deleteGoal(goalId);
                intent.putExtra("goalId", goalId);
                startActivity(intent);
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

}
