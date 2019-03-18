package com.example.al_kahtani.sygoal.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al_kahtani.sygoal.R;

/**
 * We using CursorAdapter since it's better than the ArrayAdapter.
 * The CursorAdapter have Two Implements method:
 * bindView: get the data and display it on the screen.
 * newView: inflate the xml Layout to display the data on ot.
 */
public class TaskAdapter extends CursorAdapter {

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
    public void bindView(View view, Context context, Cursor cursor) {
        //display Task Name
        TextView taskName = (TextView) view.findViewById(R.id.display_task_name);
        taskName.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Name)));

        //display Task date
        TextView date = (TextView) view.findViewById(R.id.display_task_date);
        date.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Date)));

        //display Task Alarm
        ImageView alarm = (ImageView) view.findViewById(R.id.display_task_alarm);
        int alarmColumnIndex = cursor.getColumnIndex(TaskContract.Task_Alarm);
        int mAlarm = cursor.getInt(alarmColumnIndex);
        //adjust some operation to display the correct Task Alarm.
        if (mAlarm == 0) {
            alarm.setImageResource(R.drawable.off);
        } else {
            alarm.setImageResource(R.drawable.on);
        }

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
