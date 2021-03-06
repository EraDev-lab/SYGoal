package com.daily.reach.sygoal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.daily.reach.sygoal.classes.AlarmReceiver;
import com.daily.reach.sygoal.classes.SharedPref;
import com.daily.reach.sygoal.data.GoalContract;
import com.daily.reach.sygoal.data.HelperClass;

import java.util.Locale;

public class GoalActivity extends AppCompatActivity {

    EditText goalName, goalDescription;

    RadioGroup radioGroup;

    RadioButton jobRadioButton, houseWorkRadioButton, educationRadioButton, exerciseRadioButton,
            socialRadioButton, otherRadioButton;
    ImageView imgJob, imgHousework, imgEducation, imgExercise, imgSocial, imgOther;

    Button saveGoal, deleteGoal, cancelGoal;

    int selectedType = 0;
    long goalId;
    int goalActivityNumber;
    String mGoalName;
    String mGoalDescription;
    String updateGoal = "0";
    String updateTask = "0";
    double percentage;
    String maxDate;
    int random;
    int completeGoalCount = 0;
    int completeGoalAll = 0;

    SharedPref sharedpref;
    HelperClass helper;
    SQLiteDatabase db;

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
        setContentView(R.layout.activity_goal);

        Intent intent = getIntent();

        goalId = intent.getLongExtra("goalId", goalId);
        updateGoal = intent.getStringExtra("updateGoal");
        goalActivityNumber = intent.getIntExtra("goalActivity", goalActivityNumber);

        helper = new HelperClass(this);
        //find EditText by id
        goalName = findViewById(R.id.goal_name);
        goalDescription = findViewById(R.id.goal_description);
        //find RadioButton by id
        jobRadioButton = findViewById(R.id.job_radiobutton);
        houseWorkRadioButton = findViewById(R.id.housework_radiobutton);
        educationRadioButton = findViewById(R.id.education_radiobutton);
        exerciseRadioButton = findViewById(R.id.exercise_radiobutton);
        socialRadioButton = findViewById(R.id.socail_radiobutton);
        otherRadioButton = findViewById(R.id.other_radiobutton);
        //find ImageView by id
        imgJob = findViewById(R.id.job);
        imgEducation = findViewById(R.id.education);
        imgExercise = findViewById(R.id.exercise);
        imgHousework = findViewById(R.id.housework);
        imgSocial = findViewById(R.id.social);
        imgOther = findViewById(R.id.other);
        //find Button by id
        saveGoal = findViewById(R.id.save_goal);
        deleteGoal = findViewById(R.id.delete_goal);
        cancelGoal = findViewById(R.id.cancel_goal);
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

        if (sharedpref.loadNightModeState() == true) {
            imgJob.setImageResource(R.drawable.job_white);
            imgSocial.setImageResource(R.drawable.social_white);
            imgOther.setImageResource(R.drawable.other_white);
            imgHousework.setImageResource(R.drawable.housework_white);
            imgExercise.setImageResource(R.drawable.exercise_white);
            imgEducation.setImageResource(R.drawable.education_white);
        } else {
            imgJob.setImageResource(R.drawable.job);
            imgSocial.setImageResource(R.drawable.social);
            imgOther.setImageResource(R.drawable.other);
            imgHousework.setImageResource(R.drawable.housework);
            imgExercise.setImageResource(R.drawable.exercise);
            imgEducation.setImageResource(R.drawable.education);
        }

        if (updateGoal.equals("1")) {
            setTitle("Edit Goal");

            db = helper.getReadableDatabase();

            Cursor cursor = db.query(GoalContract.TABLE_NAME,
                    new String[]{GoalContract._ID,
                            GoalContract.Goal_Name,
                            GoalContract.Goal_Type,
                            GoalContract.Goal_Activity,
                            GoalContract.Goal_Complete_Count,
                            GoalContract.Goal_Complete_All,
                            GoalContract.Goal_Percentage,
                            GoalContract.Goal_MaxDate,
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
            goalId = cursor.getInt(cursor.getColumnIndex(GoalContract._ID));
            mGoalName = cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Name));
            selectedType = cursor.getInt(cursor.getColumnIndex(GoalContract.Goal_Type));
            mGoalDescription = cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Description));

            switch (selectedType) {
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
        } else if (updateGoal.equals("0")) {
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
                if (mGoalName.isEmpty() ) {
                    if (mGoalName.isEmpty()) {
                        goalName.setError(getString(R.string.goal_is_required));
                        goalName.requestFocus();
                    }

                   /* || mGoalDescription.isEmpty() this put in input parameter
                   if (mGoalDescription.isEmpty()) {
                        goalDescription.setError(getString(R.string.description_is_required));
                        goalDescription.requestFocus();
                    }*/
                    return;
                } else if (updateGoal.equals("0")) {
                    Intent intent = new Intent(GoalActivity.this, TaskActivity.class);
                    updateGoal = "0";
                    percentage = 00.0;
                    maxDate = "2019-3-17";

                    goalId = helper.insertGoal(mGoalName, selectedType, mGoalDescription, maxDate, percentage, goalActivityNumber, completeGoalCount, completeGoalAll);

                    intent.putExtra("goalId", goalId);
                    //intent.putExtra("updateGoal", updateGoal);
                    intent.putExtra("updateTask", updateTask);
                    startActivity(intent);
                } else if (updateGoal.equals("1")) {
                    Intent intent = new Intent(GoalActivity.this, BottomNavigationViewActivity.class);
                    helper.updateGoal(goalId, mGoalName, selectedType, mGoalDescription, goalActivityNumber);
                    updateGoal = "1";
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

        cancelGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoalActivity.this, AlarmReceiver.class);

                random = (int) goalId;

                PendingIntent pendingIntent = PendingIntent.getBroadcast(GoalActivity.this, random, intent, 0);

                //PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this, goalId, intent,0);
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarm.cancel(pendingIntent);

                Intent intent1 = new Intent(GoalActivity.this, BottomNavigationViewActivity.class);
                intent1.putExtra("goalId", goalId);
                startActivity(intent1);
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
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), BottomNavigationViewActivity.class);
        startActivity(i);
        finish();
    }
}
