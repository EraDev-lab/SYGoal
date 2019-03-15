package com.example.al_kahtani.sygoal.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al_kahtani.sygoal.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskAdapter extends CursorAdapter {


    public TaskAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView taskName = (TextView) view.findViewById(R.id.display_task_name);
        taskName.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Name)));

        TextView date = (TextView) view.findViewById(R.id.display_task_date);
        date.setText(cursor.getString(cursor.getColumnIndex(TaskContract.Task_Date)));

        ImageView alarm = (ImageView) view.findViewById(R.id.display_task_alarm);
        int alarmColumnIndex = cursor.getColumnIndex(TaskContract.Task_Alarm);
        int mAlarm = cursor.getInt(alarmColumnIndex);
        if(mAlarm == 0){
            alarm.setImageResource(R.drawable.off);
        }
        else {
            alarm.setImageResource(R.drawable.on);
        }

        ImageView checkBox = (ImageView) view.findViewById(R.id.display_task_checkbox);
        int checkBoxColumnIndex = cursor.getColumnIndex(TaskContract.Task_CheckBox_Completed);
        int mCheckBox = cursor.getInt(checkBoxColumnIndex);
        if (mCheckBox == 1) {
            checkBox.setImageResource(R.drawable.ic_check_box_black_24dp);
        }
        else if (mCheckBox == 0){
            checkBox.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        }
    }
}
