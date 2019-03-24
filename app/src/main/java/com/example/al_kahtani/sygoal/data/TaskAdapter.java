package com.example.al_kahtani.sygoal.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.al_kahtani.sygoal.R;
import com.example.al_kahtani.sygoal.TaskActivity;
import com.example.al_kahtani.sygoal.classes.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * We using CursorAdapter since it's better than the ArrayAdapter.
 * The CursorAdapter have Two Implements method:
 * bindView: get the data and display it on the screen.
 * newView: inflate the xml Layout to display the data on ot.
 */
public class TaskAdapter extends CursorAdapter {
    private int notificationId = 1;

    //Constructor
    public TaskAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    //inflate the xml Layout to display the data on ot.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
    }

    // get the data and display it on the screen.
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        //display Task Name
        TextView taskName = (TextView) view.findViewById(R.id.display_task_name);
        taskName.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Name)));

        //display Task date
        TextView date = (TextView) view.findViewById(R.id.display_task_date);
        date.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Date)));

        //display Task Alarm
        final ImageView alarm = (ImageView) view.findViewById(R.id.display_task_alarm);
        final int alarmColumnIndex = cursor.getColumnIndex(TaskContract.Task_Alarm);
        final int mAlarm = cursor.getInt(alarmColumnIndex);
        //adjust some operation to display the correct Task Alarm.
        if (mAlarm == 0) { // motwakel, please replace mAlarm value of mNotify and save value in database
            alarm.setImageResource(R.drawable.off);
        } else {
            alarm.setImageResource(R.drawable.on);
        }
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // motwakel, please replace mAlarm value of mNotify and save value in database
                if (mAlarm == 0) {
                    Toast.makeText(context, "0", Toast.LENGTH_LONG).show();
                    alarm.setImageResource(R.drawable.on);
                    // mAlarm==1;// motwakel, save new value in database
                    String startDate = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Date));
                    String startTime = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Notify_On));

                    String myDate = startDate + " " + startTime;
                    Toast.makeText(context, myDate, Toast.LENGTH_LONG).show();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = null;
                    try {
                        date = sdf.parse(myDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Long alerttime;
                    alerttime = date.getTime();
                    int random = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Id)));//get key
                    Toast.makeText(context, random, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, AlarmReceiver.class);
                    intent.putExtra("notificationId", notificationId);
                    intent.putExtra("todo", cursor.getString(cursor.getColumnIndex(TaskContract.Task_Name)));
                    intent.putExtra("random", random);
                    String repeat = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Alarm));//
                    // PendingIntent alarmIntent = PendingIntent.getBroadcast(ahmed.this, 1,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, random, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarm = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                    //alarm.setRepeating +AlarmManager.INTERVAL_HOUR  for alarm Repeating
                    if (repeat.equalsIgnoreCase("2")) {
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY, alarmIntent);
                    } else if (repeat.equalsIgnoreCase("3")) {
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    } else if (repeat.equalsIgnoreCase("4")) {
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY * 30, alarmIntent);
                    } else if (repeat.equalsIgnoreCase("5")) {
                        alarm.setRepeating(AlarmManager.RTC_WAKEUP, alerttime, AlarmManager.INTERVAL_DAY * 365, alarmIntent);
                    } else if (repeat.equalsIgnoreCase("1")) {
                        alarm.set(AlarmManager.RTC_WAKEUP, alerttime, alarmIntent);
                    }

                } else {
                    Toast.makeText(context, "1", Toast.LENGTH_LONG).show();
                    // mAlarm=0;// save new value in database
                    alarm.setImageResource(R.drawable.off);

                    Intent intent = new Intent(context, AlarmReceiver.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Id))), intent, 0);

                    //PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this, goalId, intent,0);
                    AlarmManager alarm = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

                    alarm.cancel(pendingIntent);
                }
            }
        });
        //display Task CheckBoxCompleted
        ImageView checkBox = (ImageView) view.findViewById(R.id.display_task_checkbox);
        int checkBoxColumnIndex = cursor.getColumnIndex(TaskContract.Task_CheckBox_Completed);
        int mCheckBox = cursor.getInt(checkBoxColumnIndex);
        //adjust some operation to display the correct Task CheckBox Completed.
        if (mCheckBox == 1) {
            checkBox.setImageResource(R.drawable.ic_check_box_black_24dp);
        } else if (mCheckBox == 0) {
            checkBox.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        }
    }


}
