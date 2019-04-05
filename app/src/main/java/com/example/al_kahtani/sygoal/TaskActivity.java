package com.example.al_kahtani.sygoal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.al_kahtani.sygoal.classes.AlarmReceiver;
import com.example.al_kahtani.sygoal.classes.SharedPref;
import com.example.al_kahtani.sygoal.data.GoalContract;
import com.example.al_kahtani.sygoal.data.HelperClass;
import com.example.al_kahtani.sygoal.data.TaskAdapter;
import com.example.al_kahtani.sygoal.data.TaskContract;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TaskActivity extends AppCompatActivity {

    private Button saveTask, cancelTask, deleteTask;
    private ImageView setTime, setDate;
    private Spinner spinnerrepeat;
    TextView taskNotifyOn,taskDate,textCheckBoxCompleted;
    EditText taskName;
    CheckBox checkBoxCompleted;

    int random=0;
    private int notificationId = 1;
    int year, month, day, hour, minute;
    long alarmStartTime;
    long goalId;
    long taskId;
    boolean isnotifyactive=false;
    String startDate;
    String  startTime;
    int mCheckBox;
    int mTaskNotifyState;
    int mTaskAlarm;
    int notifyState = 0;
    String sTaskAlarm;

    String mTaskName;
    String updateTask = "0";

    Calendar mCurrentDate;
    Calendar mCurrentTime;
    NotificationManager notificationManager;
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
        setContentView(R.layout.activity_task);

        helper = new HelperClass(this);

        //get intent of goalId, taskId, and updateTask
        Intent intent =getIntent();
        goalId =    intent.getLongExtra("goalId", goalId);
        taskId =    intent.getLongExtra("taskId", taskId);
        updateTask = intent.getStringExtra("updateTask");

        //find EditText by goalId
        taskName = findViewById(R.id.task_name);
        //find TextView by goalId
        taskDate = findViewById(R.id.task_date);
        taskNotifyOn = findViewById(R.id.task_notify_on);
        textCheckBoxCompleted = findViewById(R.id.complete_text_view);
        //find Spinner by goalId
        spinnerrepeat = findViewById(R.id.task_alarm);
        //find CheckBox by goalId
        checkBoxCompleted = findViewById(R.id.checkbox_completed);
        //find Button by goalId
        saveTask =  findViewById(R.id.save_task);
        cancelTask = findViewById(R.id.cancel_task);
        deleteTask = findViewById(R.id.delete_task);
        //find ImageView by goalId
        setDate = findViewById(R.id.logo_date);
        setTime = findViewById(R.id.logo_time);

        /**
         *check if we are on New Task state OR in Edit Task state..
         *
         * -------------new task---------------------
         */

        if (updateTask.equals("0")){
            setTitle("New Task");
             deleteTask.setVisibility(View.GONE);
             checkBoxCompleted.setVisibility(View.GONE);
             textCheckBoxCompleted.setVisibility(View.GONE);
        }
    /**
     *  ----------------update task-----------------------
     */
        else if (updateTask.equals("1")){
            setTitle("Edit Task");

            //get the task information from database and assign it on some variables
            db = helper.getReadableDatabase();

            Cursor cursor = db.query(TaskContract.TABLE_NAME,
                    new String[]{TaskContract.Task_Id,
                            TaskContract.Task_Goal_Id,
                            TaskContract.Task_Name,
                            TaskContract.Task_Date,
                            TaskContract.Task_Notify_On,
                            TaskContract.Task_NotifyState,
                            TaskContract.Task_Alarm,
                            TaskContract.Task_CheckBox_Completed},

                    TaskContract.Task_Id + "=?",
                    new String[]{String.valueOf(taskId)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

            // prepare contract object
            taskId = cursor.getLong(cursor.getColumnIndex(TaskContract.Task_Id));
            goalId = cursor.getLong(cursor.getColumnIndex(TaskContract.Task_Goal_Id));
            mTaskName = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Name));
            startDate = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Date));
            startTime = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Notify_On));
            mTaskAlarm = cursor.getInt(cursor.getColumnIndex(TaskContract.Task_Alarm));
            mTaskNotifyState = cursor.getInt(cursor.getColumnIndex(TaskContract.Task_NotifyState));
            mCheckBox = cursor.getInt(cursor.getColumnIndex(TaskContract.Task_CheckBox_Completed));

            // close the db connection
            cursor.close();
            db.close();

            taskName.setText(mTaskName);
            taskDate.setText(startDate);
            taskNotifyOn.setText(startTime);

            /* ToDo: problem in this..
            if (mTaskAlarm.equals("Off")){
                spinnerrepeat.getItemAtPosition(0);
                position = 0;
            }
            else if (mTaskAlarm.equals("Once")){
                spinnerrepeat.getItemAtPosition(1);
                position = 1;
            }
            else if (mTaskAlarm.equals("Daily")){
                spinnerrepeat.getItemAtPosition(2);
                position = 2;
            }
            else if (mTaskAlarm.equals("Weakly")){
                spinnerrepeat.getItemAtPosition(3);
                position = 3;
            }
            else if (mTaskAlarm.equals("Monthly")){
                spinnerrepeat.getItemAtPosition(4);
                position = 4;
            }
            else if (mTaskAlarm.equals("Yearly")){
                spinnerrepeat.getItemAtPosition(5);
                position = 5;
            }
            */


            if (mCheckBox == 1) {
                checkBoxCompleted.setChecked(true);
            }
            else if (mCheckBox == 0)
                checkBoxCompleted.setChecked(false);
        }

        notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//to start calender
        ImageGenerator mImageGenerator = new ImageGenerator(TaskActivity.this);

// Set the icon size to the generated in dip.
        mImageGenerator.setIconSize(50, 50);

// Set the size of the date and month font in dip.
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

// Set the position of the date and month in dip.
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

// Set the color of the font to be generated
        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);
//select date
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate = Calendar.getInstance();
                year=mCurrentDate.get(Calendar.YEAR);
                month=mCurrentDate.get(Calendar.MONTH);
                day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
                //final String abc= getAge(year, month, day);

                DatePickerDialog mPickerDialog =  new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                        startDate = Year + "-" + (Month+1) + "-" + Day;
                        taskDate.setText(startDate);
                        //  Toast.makeText(RegisterPatientActivity.this,"Your age= "+abc, Toast.LENGTH_LONG).show();

                        mCurrentDate.set(Year, (Month+1),Day);
                        //   mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                    }
                }, year, month, day);
                mPickerDialog.show();
            }
        });
        ///////////////////*Calender////////////////////---------------------

         //select time

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentTime = Calendar.getInstance();
                hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                minute = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime = selectedHour + ":" + selectedMinute;
                        taskNotifyOn.setText( startTime);
                        // alarmStartTime = mCurrentTime.getTimeInMillis();
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        // spinner for repeating
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(
                TaskActivity.this, R.array.repeating_array, android.R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(R.layout.spinner_list_item);
        spinnerrepeat.setAdapter(adapters);

        spinnerrepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                //String minsurance = spinnerinsurance.getSelectedItem().toString().trim();
                sTaskAlarm = spinnerrepeat.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //to save time and date for notification system
        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // convert time from string to date variable
                // to force user to enter task data
                mTaskName = taskName.getText().toString();
                startDate = taskDate.getText().toString();
                startTime = taskNotifyOn.getText().toString();

                if (mTaskName.isEmpty() || startDate.isEmpty() || startTime.isEmpty()) {
                    if (mTaskName.isEmpty()) {
                        taskName.setError(getString(R.string.task_is_required));
                        taskName.requestFocus();
                    }

                    if (startDate.isEmpty()) {
                        taskDate.setError(getString(R.string.date_is_required));
                        taskDate.requestFocus();
                    }

                    if (startTime.isEmpty()) {
                        taskNotifyOn.setError(getString(R.string.time_is_required));
                        taskNotifyOn.requestFocus();
                    }
                return;
                }

                //store the variable of alarm
                if (sTaskAlarm.equals("Off")){
                    mTaskAlarm = 0;
                }
                else if(sTaskAlarm.equals("Once")){
                    mTaskAlarm = 1;
                }
                else if(sTaskAlarm.equals("Daily")){
                    mTaskAlarm = 2;
                }
                else if(sTaskAlarm.equals("Weekly")){
                    mTaskAlarm = 3;
                }
                else if(sTaskAlarm.equals("Monthly")){
                    mTaskAlarm = 4;
                }
                else if(sTaskAlarm.equals("Yearly")){
                    mTaskAlarm = 5;
                }

                if (sTaskAlarm.equals("Once") || sTaskAlarm.equals("Daily") || sTaskAlarm.equals("Weekly") ||
                        sTaskAlarm.equals("Monthly") || sTaskAlarm.equals("Yearly")){
                    notifyState = 1;
                }
                else if (sTaskAlarm.equals("Off")){
                    notifyState = 0;
                }
                //store the variable of checkbox
                if (!checkBoxCompleted.isChecked()){
                    mCheckBox = 0;
                }
                else if (checkBoxCompleted.isChecked()){
                    mCheckBox = 1;
                }

                String myDate = startDate + " " + startTime;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //long millis = date.getTime();
                // Long alerttime=new GregorianCalendar().getTimeInMillis()+5*1000;
                Long alerttime;
                alerttime = date.getTime();
                random = (int)taskId;
                Intent intent = new Intent(TaskActivity.this, AlarmReceiver.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("todo", taskName.getText().toString());
                intent.putExtra("random", random);
                String repeat = spinnerrepeat.getSelectedItem().toString().trim();
                // PendingIntent alarmIntent = PendingIntent.getBroadcast(ahmed.this, 1,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(TaskActivity.this, random, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                //alarm.setRepeating +AlarmManager.INTERVAL_HOUR  for alarm Repeating
                if (repeat.equalsIgnoreCase("Daily")) {
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY, alarmIntent);
                } else if (repeat.equalsIgnoreCase("Weakly")) {
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                } else if (repeat.equalsIgnoreCase("Monthly")) {
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY * 30, alarmIntent);
                } else if (repeat.equalsIgnoreCase("Yearly")) {
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY * 365, alarmIntent);
                } else if (repeat.equalsIgnoreCase("Once")) {
                    alarm.set(AlarmManager.RTC_WAKEUP, alerttime, alarmIntent);
                }

                if (startDate.equals("") || startTime.equals("") || mTaskName.equals("")){
                    Toast.makeText(TaskActivity.this, "Complete all of your info", Toast.LENGTH_SHORT).show();
                }

                else if (updateTask.equals("0")) {

                    Intent i = new Intent(TaskActivity.this, DisplayTaskScreen.class);
                    helper.insertTask(goalId, mTaskName, startDate, startTime, mTaskAlarm, mCheckBox, notifyState);
                    i.putExtra("goalId", goalId);
                    startActivity(i);
                }
                else if (updateTask.equals("1")){
                    Intent i = new Intent(TaskActivity.this, DisplayTaskScreen.class);
                    helper.updateTask(taskId, mTaskName, startDate, startTime, mTaskAlarm, mCheckBox, notifyState);
                    i.putExtra("goalId", goalId);
                    startActivity(i);
                }
            }
        });
// cancel notefication
        cancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, AlarmReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(TaskActivity.this, random,intent, 0);

                //PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this, goalId, intent,0);
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarm.cancel(pendingIntent);

                Intent  intent1 = new Intent(TaskActivity.this, DisplayTaskScreen.class);
                intent1.putExtra("goalId", goalId);
                startActivity(intent1);
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, AlarmReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(TaskActivity.this, random,intent, 0);

                //PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this, goalId, intent,0);
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarm.cancel(pendingIntent);

                Intent intent1 = new Intent(TaskActivity.this, DisplayTaskScreen.class);
                helper.deleteTask(taskId);
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

}
