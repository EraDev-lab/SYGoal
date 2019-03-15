package com.example.al_kahtani.sygoal.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class HelperClass extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final String DATABASE_NAME = "goals.db";

    private static final int DATABASE_VERSION = 6 ;

    String CREATE_GOAL_TABLE = "CREATE TABLE " + GoalContract.TABLE_NAME + " ("
            + GoalContract._ID + " INTEGER  PRIMARY KEY  AUTOINCREMENT , "
            + GoalContract.Goal_Name + " TEXT NOT NULL, "
            + GoalContract.Goal_Type + " INTEGER NOT NULL, "
            + GoalContract.Goal_Activity + " INTEGER NOT NULL, "
            + GoalContract.Goal_Description + " TEXT NOT NULL);";

    String CREATE_TASK_TABLE = "CREATE TABLE " + TaskContract.TABLE_NAME + " ("
            + TaskContract.Task_Id + " INTEGER  PRIMARY KEY  AUTOINCREMENT , "
            + TaskContract.Task_Goal_Id + " INTEGER  NOT NULL, "
            + TaskContract.Task_Name + " TEXT NOT NULL, "
            + TaskContract.Task_Date + " DATE NOT NULL, "
            + TaskContract.Task_Alarm + " INTEGER NOT NULL, "
            + TaskContract.Task_Notify_On + " TIME NOT NULL, "
            + TaskContract.Task_CheckBox_Completed + " INTEGER NOT NULL);";


    public HelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GOAL_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GoalContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE_NAME);
        onCreate(db);
    }

    //-----------------------------------our Goal Operation-------------------------------------------------


    final public long insertGoal(String name, int type, String description, int activity) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(GoalContract.Goal_Name, name);
        values.put(GoalContract.Goal_Activity, activity);
        values.put(GoalContract.Goal_Type, type);
        values.put(GoalContract.Goal_Description, description);

        // insert row
        long id = db.insert(GoalContract.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


   
    //________________________________________________________________________________
    public int updateGoal(long goalId, String name, int type, String description, int activity) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GoalContract.Goal_Name, name);
        values.put(GoalContract.Goal_Type, type);
        values.put(GoalContract.Goal_Activity, activity);
        values.put(GoalContract.Goal_Description, description);

        String selection = GoalContract._ID + " = ?";
        String selectionArgs[] = new String[]{String.valueOf(goalId)};
        // updating row
        return db.update(GoalContract.TABLE_NAME, values, selection, selectionArgs);
    }

    //________________________________________________________________________________
    public void deleteGoal( long id) {
        db = this.getWritableDatabase();

        db.delete(GoalContract.TABLE_NAME, GoalContract._ID + " = ?",
                new String[]{String.valueOf(id)});

        deleteWithTask(id);
        db.close();
    }
    public void deleteWithTask(long id){
        String selectQuery = "SELECT  * FROM " + TaskContract.TABLE_NAME + " Where " + TaskContract.Task_Goal_Id
                + " = " + id;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int i =cursor.getInt(cursor.getColumnIndex(TaskContract.Task_Id));
                long currentId = Long.valueOf(i);

                deleteTask(currentId);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();
    }

    //--------------------------------------------------------------------------------------------------------
    //-------------------------------------our Task operation-------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

    final public long insertTask(long taskGoalId, String name, String date, String notifyOn, int alarm, int checkBoxCompleted) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(TaskContract.Task_Goal_Id, taskGoalId);
        values.put(TaskContract.Task_Name, name);
        values.put(TaskContract.Task_Date, date);
        values.put(TaskContract.Task_Notify_On, notifyOn);
        values.put(TaskContract.Task_Alarm, alarm);
        values.put(TaskContract.Task_CheckBox_Completed, checkBoxCompleted);

        // insert row
        long id = db.insert(TaskContract.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    //________________________________________________________________________________
    public int updateTask(long taskId, String name, String date, String notifyOn, int alarm, int checkBox) {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.Task_Name, name);
        values.put(TaskContract.Task_Date, date);
        values.put(TaskContract.Task_Notify_On, notifyOn);
        values.put(TaskContract.Task_Alarm, alarm);
        values.put(TaskContract.Task_CheckBox_Completed, checkBox);

        String selection = TaskContract.Task_Id + " = ?";
        String selectionArgs[] = new String[]{String.valueOf(taskId)};
        // updating row
        return db.update(TaskContract.TABLE_NAME, values, selection, selectionArgs);
    }

    //________________________________________________________________________________
    public void deleteTask(long id) {
        db = this.getWritableDatabase();

        db.delete(TaskContract.TABLE_NAME, TaskContract.Task_Id + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

}
