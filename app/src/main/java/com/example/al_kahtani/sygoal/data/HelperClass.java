package com.example.al_kahtani.sygoal.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperClass extends SQLiteOpenHelper {

    //DataBase Name
    private static final String DATABASE_NAME = "goals.db";
    //DataBase Version
    private static final int DATABASE_VERSION = 7;
    SQLiteDatabase db;
    //Create Goal Table Query
    String CREATE_GOAL_TABLE = "CREATE TABLE " + GoalContract.TABLE_NAME + " ("
            + GoalContract._ID + " INTEGER  PRIMARY KEY  AUTOINCREMENT , "
            + GoalContract.Goal_Name + " TEXT NOT NULL, "
            + GoalContract.Goal_Type + " INTEGER NOT NULL, "
            + GoalContract.Goal_MaxDate + " DATE NOT NULL, "
            + GoalContract.Goal_Percentage + " INTEGER NOT NULL, "
            + GoalContract.Goal_Activity + " INTEGER NOT NULL, "
            + GoalContract.Goal_Description + " TEXT NOT NULL);";

    //Create Task Table Query
    String CREATE_TASK_TABLE = "CREATE TABLE " + TaskContract.TABLE_NAME + " ("
            + TaskContract.Task_Id + " INTEGER  PRIMARY KEY  AUTOINCREMENT , "
            + TaskContract.Task_Goal_Id + " INTEGER  NOT NULL, "
            + TaskContract.Task_Name + " TEXT NOT NULL, "
            + TaskContract.Task_Date + " DATE NOT NULL, "
            + TaskContract.Task_Alarm + " INTEGER NOT NULL, "
            + TaskContract.Task_Notify_On + " TIME NOT NULL, "
            + TaskContract.Task_CheckBox_Completed + " INTEGER NOT NULL);";

    //Constructor
    public HelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //on Create DataBase it will Create out Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GOAL_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    //On Upgrade DataBase it will Upgrade our dataBase on user clear the database..
    //.. from the cache memory or on the DataBase Version updated.

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GoalContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE_NAME);
        onCreate(db);
    }

    //-----------------------------------our Goal Operation-------------------------------------------------

    //insert data into the Goal Table
    final public long insertGoal(String name, int type, String description, String maxDate, double percentage, int activity) {
        //get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //Goal ID will be inserted automatically.
        values.put(GoalContract.Goal_Name, name);
        values.put(GoalContract.Goal_MaxDate, maxDate);
        values.put(GoalContract.Goal_Percentage, percentage);
        values.put(GoalContract.Goal_Activity, activity);
        values.put(GoalContract.Goal_Type, type);
        values.put(GoalContract.Goal_Description, description);

        //insert row
        long id = db.insert(GoalContract.TABLE_NAME, null, values);

        //close database connection
        db.close();

        //return newly inserted row id
        return id;
    }


    //________________________________________________________________________________

    //update data from the Goal Table
    public int updateGoal(long goalId, String name, int type, String description, int activity) {
        //get writable database as we want to write data
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(GoalContract.Goal_Name, name);
        values.put(GoalContract.Goal_Type, type);
        values.put(GoalContract.Goal_Activity, activity);
        values.put(GoalContract.Goal_Description, description);

        //the where clause (SELECT * FROM TABLE_NAME -WHERE- ..)
        String selection = GoalContract._ID + " = ?";
        String selectionArgs[] = new String[]{String.valueOf(goalId)};

        //updating row
        return db.update(GoalContract.TABLE_NAME, values, selection, selectionArgs);
    }

    //other needed update method
    public int updateGoal(long goalId, String maxDate, double percentage, int activity) {
        //get writable database as we want to write data
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(GoalContract.Goal_MaxDate, maxDate);
        values.put(GoalContract.Goal_Percentage, percentage);
        values.put(GoalContract.Goal_Activity, activity);

        //the where clause (SELECT * FROM TABLE_NAME -WHERE- ..)
        String selection = GoalContract._ID + " = ?";
        String selectionArgs[] = new String[]{String.valueOf(goalId)};

        //updating row
        return db.update(GoalContract.TABLE_NAME, values, selection, selectionArgs);
    }

    //________________________________________________________________________________

    //delete data from the Goal Table with condition
    public void deleteGoal(long id) {
        //get writable database as we want to write data
        db = this.getWritableDatabase();

        //deleting data where id = ~~
        db.delete(GoalContract.TABLE_NAME, GoalContract._ID + " = ?",
                new String[]{String.valueOf(id)});

        //delete the Goal Tasks too to give a free Memory resources
        deleteWithTask(id);
        //close the database connection
        db.close();
    }

    public void deleteWithTask(long id) {
        //getting all the Tasks of the specific deleting Goal
        String selectQuery = "SELECT  * FROM " + TaskContract.TABLE_NAME + " Where " + TaskContract.Task_Goal_Id
                + " = " + id;
        //get writable database as we want to write data
        db = this.getWritableDatabase();
        //get all of the specifics Tasks in the cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all Task rows
        if (cursor.moveToFirst()) {
            do {
                //getting the Task ID to delete it from the DataBase
                long currentId = cursor.getLong(cursor.getColumnIndex(TaskContract.Task_Id));

                //then call the delete task method to delete it
                deleteTask(currentId);
            } while (cursor.moveToNext());
        }

        //close database connection
        db.close();
    }

    //--------------------------------------------------------------------------------------------------------
    //-------------------------------------our Task operation-------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

    final public long insertTask(long taskGoalId, String name, String date, String notifyOn, int alarm, int checkBoxCompleted) {
        //get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //Task ID will be inserted automatically.

        values.put(TaskContract.Task_Goal_Id, taskGoalId);
        values.put(TaskContract.Task_Name, name);
        values.put(TaskContract.Task_Date, date);
        values.put(TaskContract.Task_Notify_On, notifyOn);
        values.put(TaskContract.Task_Alarm, alarm);
        values.put(TaskContract.Task_CheckBox_Completed, checkBoxCompleted);

        //insert row
        long id = db.insert(TaskContract.TABLE_NAME, null, values);

        //close database connection
        db.close();

        //return newly inserted row id
        return id;
    }

    //________________________________________________________________________________

    //update data from the Task Table
    public int updateTask(long taskId, String name, String date, String notifyOn, int alarm, int checkBox) {
        //get writable database as we want to write data
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.Task_Name, name);
        values.put(TaskContract.Task_Date, date);
        values.put(TaskContract.Task_Notify_On, notifyOn);
        values.put(TaskContract.Task_Alarm, alarm);
        values.put(TaskContract.Task_CheckBox_Completed, checkBox);

        //the where clause (SELECT * FROM TABLE_NAME -WHERE- ..)
        String selection = TaskContract.Task_Id + " = ?";
        String selectionArgs[] = new String[]{String.valueOf(taskId)};

        //updating row
        return db.update(TaskContract.TABLE_NAME, values, selection, selectionArgs);
    }

    //________________________________________________________________________________

    //delete data from the Task Table
    public void deleteTask(long id) {
        //get writable database as we want to write data
        db = this.getWritableDatabase();

        //deleting data where id = ~~
        db.delete(TaskContract.TABLE_NAME, TaskContract.Task_Id + " = ?",
                new String[]{String.valueOf(id)});

        //close the database connection
        db.close();
    }
}
