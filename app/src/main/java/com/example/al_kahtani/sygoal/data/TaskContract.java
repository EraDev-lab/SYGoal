package com.example.al_kahtani.sygoal.data;

import android.provider.BaseColumns;

public class TaskContract implements BaseColumns {

    //Task Table Name
    public static final String TABLE_NAME = "Task";

    //Task columns
    public static final String Task_Id = BaseColumns._ID;
    public static final String Task_Name = "task_name";
    public static final String Task_Date = "task_date";
    public static final String Task_Notify_On = "task_notify_on";
    public static final String Task_Alarm = "task_alarm";
    public static final String Task_CheckBox_Completed = "task_checkbox_completed";
    public static final String Task_Goal_Id = "task_goal_id";

    //constructor
    public TaskContract() {
    }
}
