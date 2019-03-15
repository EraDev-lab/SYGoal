package com.example.al_kahtani.sygoal.data;

import android.provider.BaseColumns;

public class TaskContract implements BaseColumns {
    public static final String TABLE_NAME = "Task";

    public static final String Task_Id = BaseColumns._ID;
    public static final String Task_Name = "task_name";
    public static final String Task_Date = "task_date";
    public static final String Task_Notify_On = "task_notify_on";
    public static final String Task_Alarm = "task_alarm";
    public static final String Task_CheckBox_Completed = "task_checkbox_completed";
    public static final String Task_Goal_Id = "task_goal_id";


    public static final int TYPE_JOB = 0;
    public static final int TYPE_EXERCISE = 1;
    public static final int TYPE_HOUSE_WORK = 2;
    public static final int TYPE_SOCIAL = 3;
    public static final int TYPE_EDUCATION = 4;
    public static final int TYPE_OTHER = 5;


    private int taskId;
    private int goalId;
    private String name;
    private String date;
    private String notifyOn;
    private String alarm;
    private String checkboxCompleted;


    public TaskContract() {
    }

    public TaskContract(int taskId, int goalId, String name, String date, String notifyOn, String alarm, String checkboxCompleted) {
        this.taskId = taskId;
        this.goalId = goalId;
        this.name = name;
        this.date = date;
        this.notifyOn = notifyOn;
        this.alarm = alarm;
        this.checkboxCompleted = checkboxCompleted;
    }

    public TaskContract(String name, String date, String notifyOn, String alarm, String checkboxCompleted) {
        this.name = name;
        this.date = date;
        this.notifyOn = notifyOn;
        this.alarm = alarm;
        this.checkboxCompleted = checkboxCompleted;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskGoalId() {
        return goalId;
    }

    public void setTaskGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getTaskName() {
        return name;
    }

    public void setTaskName(String name) {
        this.name = name;
    }

    public String getTaskDate() {
        return date;
    }

    public void setTaskDate(String date) {
        this.date = date;
    }

    public String getTaskNotifyOn() {
        return notifyOn;
    }

    public void setTaskNotifyOn(String notifyOn) {
        this.notifyOn = notifyOn;
    }

    public String getTaskAlarm() {
        return alarm;
    }

    public void setTaskAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getTaskCheckBoxCompleted() {
        if (checkboxCompleted.equals("1")) {
            checkboxCompleted = "true";
        } else if (checkboxCompleted.equals("0")) {
            checkboxCompleted = "false";
        }
        return checkboxCompleted;
    }

    public void setTaskCheckBoxCompleted(String checkboxCompleted) {
        this.checkboxCompleted = checkboxCompleted;
    }
}
