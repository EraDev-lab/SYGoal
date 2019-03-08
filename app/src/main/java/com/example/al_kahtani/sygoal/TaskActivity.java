package com.example.al_kahtani.sygoal;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.al_kahtani.sygoal.classes.AlarmReceiver;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TaskActivity extends AppCompatActivity {
    private Button saveBtn,cancelBtn,deleteBtn;
    private ImageView detecttime,detectdate;
    private Spinner spinnerrepeat;
    EditText editTextTask,editTextDate,editTextTime;
    int random=0;
    private int notificationId = 1;
    long alarmStartTime;
    Calendar mCurrentDate;
    int year,month,day;
    String caltext;
    String  startTime;
    boolean isnotifyactive=false;
    NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Set Onclick Listener.
        detectdate= findViewById(R.id.logo_date);
        detecttime= findViewById(R.id.logo_time);

        saveBtn= findViewById(R.id.save);
        cancelBtn=findViewById(R.id.cancle);
        deleteBtn=findViewById(R.id.delete);

        editTextTask = findViewById(R.id.edit_tasks);
        editTextDate = findViewById(R.id.edit_date);
        editTextTime = findViewById(R.id.edit_notify);
        spinnerrepeat= findViewById(R.id.Spinner);

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
        detectdate.setOnClickListener(new View.OnClickListener() {
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
                        caltext=Year+"_"+ (Month+1)+"_"+Day;
                        editTextDate.setText(caltext);
                        //  Toast.makeText(RegisterPatientActivity.this,"Your age= "+abc, Toast.LENGTH_LONG).show();

                        mCurrentDate.set(Year, (Month+1),Day);
                        //   mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                    }
                }, year, month, day);
                mPickerDialog.show();
            }
        });///////////////////*Calender////////////////////---------------------

//select time

        detecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        startTime=selectedHour + ":" + selectedMinute;

                        editTextTime.setText( startTime);
                        // alarmStartTime = mcurrentTime.getTimeInMillis();

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //to save time and date for notification system
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// convert time from string to date variable
                String myDate = caltext+" "+startTime;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //long millis = date.getTime();
                // Long alerttime=new GregorianCalendar().getTimeInMillis()+5*1000;
                Long alerttime= date.getTime();
                random = new Random().nextInt(100000)  ;
                Intent intent = new Intent(TaskActivity.this, AlarmReceiver.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("todo", editTextTask.getText().toString());
                intent.putExtra("random", random);
                String repeat= spinnerrepeat.getSelectedItem().toString().trim();
                // PendingIntent alarmIntent = PendingIntent.getBroadcast(ahmed.this, 1,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(TaskActivity.this, random,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                //alarm.setRepeating +AlarmManager.INTERVAL_HOUR  for alarm Repeating
                if(repeat.equalsIgnoreCase("Daily")){
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime,AlarmManager.INTERVAL_DAY,alarmIntent);
                }else if(repeat.equalsIgnoreCase("Weakly")){
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime,AlarmManager.INTERVAL_DAY*7,alarmIntent);
                }else if(repeat.equalsIgnoreCase("Monthly")){
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime,AlarmManager.INTERVAL_DAY*30,alarmIntent);
                }else if(repeat.equalsIgnoreCase("Yearly")){
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime,AlarmManager.INTERVAL_DAY*365,alarmIntent);
                }else if(repeat.equalsIgnoreCase("Once")) {
                    alarm.set(AlarmManager.RTC_WAKEUP, alerttime, alarmIntent);
                }
                //alarm.set(AlarmManager.RTC_WAKEUP, alerttime,alarmIntent);

                //   PendingIntent.getBroadcast(ahmed.this, 1,intent, PendingIntent.FLAG_UPDATE_CURRENT));

            }
        });
// cancel notefication
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, AlarmReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(TaskActivity.this, random,intent, 0);

                //PendingIntent pendingIntent = PendingIntent.getBroadcast(Setting.this, id, intent,0);
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarm.cancel(pendingIntent);

            }
        });
    }
}
