package com.daily.reach.sygoal.data;

import android.provider.BaseColumns;

public class TaskContract implements BaseColumns {

    //Task Table Name
    public static final String TABLE_NAME = "Task";

    //Task columns
    public static final String Task_Id = BaseColumns._ID;
    public static final String Task_Goal_Id = "task_goal_id";
    public static final String Task_Name = "task_name";
    public static final String Task_Date = "task_date";
    public static final String Task_Notify_On = "task_notify_on";
    public static final String Task_NotifyState = "task_notify_state";
    public static final String Task_Alarm = "task_alarm";
    public static final String Task_CheckBox_Completed = "task_checkbox_completed";

    //constructor
    public TaskContract() {
    }
}
