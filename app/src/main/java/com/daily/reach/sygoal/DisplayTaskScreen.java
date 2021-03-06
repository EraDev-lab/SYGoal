package com.daily.reach.sygoal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daily.reach.sygoal.classes.SharedPref;
import com.daily.reach.sygoal.data.GoalContract;
import com.daily.reach.sygoal.data.HelperClass;
import com.daily.reach.sygoal.data.TaskAdapter;
import com.daily.reach.sygoal.data.TaskContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DisplayTaskScreen extends AppCompatActivity {

    FloatingActionButton fab;
    ListView taskListView;
    TextView goalName;
    TextView goalDescription;
    TextView taskPercentage;
    ProgressBar taskProgressBar;
    ProgressBar emptyTaskProgressBar;

    long goalId;
    String updateTask;
    int completeTaskCount = 0;
    int taskCount = 0;
    int newCompleteTaskCount = 0;
    int newTaskCount = 0;
    int mGoalActivity = 0;
    double newMPercentage = 00;
    String newBeginDate = "0000-1-1";
    String newMaxDate;
    String newEndDate;
    String newNextDate;
    String newCurrentDate;
    String beginDate = "0000-1-1";
    String maxDate;
    String endDate;
    String nextDate;
    String currentDate;
    String mGoalName = "";
    String mGoalDescription = "";
    int goalActivityNumber;
    int completeState;
    double mPercentage = 00;


    SharedPref sharedpref;
    HelperClass helper;
    TaskAdapter adapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);//load night mode setting
        if (sharedpref.loadNightModeState() == true) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        loadLocale();//load languge setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task_screen);

        fab = findViewById(R.id.task_fab);
        taskListView = findViewById(R.id.task_list_view);
        goalDescription = findViewById(R.id.taskactivity_goal_description);
        goalName = findViewById(R.id.taskactivity_goal_name);
        taskPercentage = findViewById(R.id.taskactivity_percentage);
        taskProgressBar = findViewById(R.id.taskactivity_progress);
        emptyTaskProgressBar = findViewById(R.id.empty_taskactivity_progress);

        Intent intent = getIntent();
        goalId = intent.getLongExtra("goalId", goalId);
        helper = new HelperClass(this);

        try {
            //open Database to read info from it
            db = helper.getWritableDatabase();

            String TaskAndGoalQuery = "SELECT g." + GoalContract.Goal_Name + ", "
                    + "g." + GoalContract.Goal_Description + ", "
                    + "t." + TaskContract.Task_Name + ", "
                    + "t." + TaskContract.Task_Date + ", "
                    + "t." + TaskContract.Task_Notify_On + ", "
                    + "t." + TaskContract.Task_Alarm + ", "
                    + "t." + TaskContract.Task_NotifyState + ", "
                    + "t." + TaskContract.Task_Goal_Id + ", "
                    + "t." + TaskContract.Task_Id + ", "
                    + "t." + TaskContract.Task_CheckBox_Completed
                    + " FROM " + TaskContract.TABLE_NAME + " t "
                    + " JOIN " + GoalContract.TABLE_NAME + " g "
                    + " ON t." + TaskContract.Task_Goal_Id + " = " + goalId
                    + " Where g." + GoalContract._ID + " = t." + TaskContract.Task_Goal_Id
                    + " ORDER BY t." + TaskContract.Task_Date + ", t." + TaskContract.Task_Notify_On + " ASC ";

            final Cursor cursor = db.rawQuery(TaskAndGoalQuery, null);
            if (cursor.getCount() != 0) {
                try {
                    while (cursor.moveToNext()) {
                        int c = cursor.getColumnIndex(TaskContract.Task_CheckBox_Completed);
                        completeState = cursor.getInt(c);

                        taskCount = taskCount + 1;
                        if (completeState == 1) {
                            completeTaskCount = completeTaskCount + 1;
                        }
                        nextDate = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Date));
                        //ToDo: ========================mNotifyState=========================
                        int mNotifyState = cursor.getInt(cursor.getColumnIndex(TaskContract.Task_NotifyState));
                        //make an update for it later.. when alarm is off => notify state is 0 else notify state is 1

                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                            Date date1 = format.parse(beginDate);
                            Date date2 = format.parse(nextDate);

                            if (date1.compareTo(date2) > 0) {
                                maxDate = beginDate;
                            } else if (date1.compareTo(date2) < 0) {
                                maxDate = nextDate;
                            } else if (date1.compareTo(date2) == 0) {
                                maxDate = nextDate;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            db.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    db = helper.getReadableDatabase();
                    Cursor cursor1 = db.query(GoalContract.TABLE_NAME,
                            new String[]{GoalContract._ID,
                                    GoalContract.Goal_Name,
                                    GoalContract.Goal_Description,
                                    GoalContract.Goal_Activity},

                            GoalContract._ID + "=?",
                            new String[]{String.valueOf(goalId)},
                            null,
                            null,
                            null,
                            null);

                    if (cursor1 != null)
                        cursor1.moveToFirst();
                    mGoalName = cursor1.getString(cursor1.getColumnIndex(GoalContract.Goal_Name));
                    mGoalActivity = cursor1.getInt(cursor1.getColumnIndex(GoalContract.Goal_Activity));
                    mGoalDescription = cursor1.getString(cursor1.getColumnIndex(GoalContract.Goal_Description));
                    cursor1.close();
                } finally {
                    db.close();
                }

                mPercentage = Math.floor((completeTaskCount * 100) / taskCount);

                taskPercentage.setText((int) mPercentage + "%");
                taskProgressBar.setProgress((int) mPercentage);
                goalName.setText(mGoalName);
                goalDescription.setText(mGoalDescription);

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                month = month + 1;

                currentDate = year + "-" + month + "-" + day;

                try {
                    endDate = maxDate;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                    Date date1 = format.parse(endDate);
                    Date date2 = format.parse(currentDate);

                    if (date1.compareTo(date2) > 0) {
                        if (completeTaskCount < taskCount) {
                            goalActivityNumber = 1;
                        } else if (completeTaskCount == taskCount) {
                            goalActivityNumber = 3;
                        }
                    } else if (date1.compareTo(date2) < 0) {
                        if (completeTaskCount < taskCount) {
                            goalActivityNumber = 2;
                        } else if (completeTaskCount == taskCount) {
                            goalActivityNumber = 3;
                        }
                    } else if (date1.compareTo(date2) == 0) {
                        if (completeTaskCount < taskCount) {
                            goalActivityNumber = 1;
                        } else if (completeTaskCount == taskCount) {
                            goalActivityNumber = 3;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                helper.updateGoal(goalId, maxDate, mPercentage, goalActivityNumber, completeTaskCount, taskCount);

            } else {
                try {
                    db = helper.getReadableDatabase();
                    Cursor cursor1 = db.query(GoalContract.TABLE_NAME,
                            new String[]{GoalContract._ID,
                                    GoalContract.Goal_Name,
                                    GoalContract.Goal_Activity},

                            GoalContract._ID + "=?",
                            new String[]{String.valueOf(goalId)},
                            null,
                            null,
                            null,
                            null);

                    if (cursor1 != null)
                        cursor1.moveToFirst();
                    mGoalName = cursor1.getString(cursor1.getColumnIndex(GoalContract.Goal_Name));
                    mGoalActivity = cursor1.getInt(cursor1.getColumnIndex(GoalContract.Goal_Activity));
                    cursor1.close();
                } finally {
                    db.close();
                }

                mPercentage = 00;

                taskPercentage.setText((int) mPercentage + "%");
                taskProgressBar.setProgress((int) mPercentage);
                goalName.setText(mGoalName);
            }

            adapter = new TaskAdapter(this, cursor);

            if (mGoalActivity == 1) {
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DisplayTaskScreen.this, TaskActivity.class);
                        intent.putExtra("goalId", goalId);
                        updateTask = "0";
                        intent.putExtra("updateTask", updateTask);
                        startActivity(intent);
                    }
                });

                taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, final long id) {
                        final PopupMenu popupMenu = new PopupMenu(DisplayTaskScreen.this, view);
                        popupMenu.inflate(R.menu.pop_up_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int selectedItem = item.getItemId();
                                if (selectedItem == R.id.update) {
                                    updateTask = "1";
                                    Intent intent = new Intent(DisplayTaskScreen.this, TaskActivity.class);
                                    intent.putExtra("taskId", id);
                                    intent.putExtra("updateTask", updateTask);
                                    startActivity(intent);

                                } else if (selectedItem == R.id.delete) {
                                    helper.deleteTask(id);
                                    Cursor cursor = updateUi();


                                    while (cursor.moveToNext()) {
                                        int c = cursor.getColumnIndex(TaskContract.Task_CheckBox_Completed);
                                        completeState = cursor.getInt(c);

                                        newTaskCount = newTaskCount + 1;
                                        if (completeState == 1) {
                                            newCompleteTaskCount = newCompleteTaskCount + 1;
                                        }

                                        newNextDate = cursor.getString(cursor.getColumnIndex(TaskContract.Task_Date));
                                        try {
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                                            Date date1 = format.parse(newBeginDate);
                                            Date date2 = format.parse(newNextDate);

                                            if (date1.compareTo(date2) > 0) {
                                                newMaxDate = newBeginDate;
                                            } else if (date1.compareTo(date2) < 0) {
                                                newMaxDate = newNextDate;
                                            } else if (date1.compareTo(date2) == 0) {
                                                newMaxDate = newNextDate;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (newTaskCount != 0) {
                                        newMPercentage = Math.floor((newCompleteTaskCount * 100) / newTaskCount);
                                    }else{
                                        newMPercentage = 0.0;
                                        newTaskCount = 0;
                                    }
                                    taskPercentage.setText((int) newMPercentage + "%");
                                    taskProgressBar.setProgress((int) newMPercentage);


                                    Calendar calendar = Calendar.getInstance();
                                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    int month = calendar.get(Calendar.MONTH);
                                    int year = calendar.get(Calendar.YEAR);
                                    month = month + 1;

                                    newCurrentDate = year + "-" + month + "-" + day;

                                    try {
                                        newEndDate = newMaxDate;
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                                        Date date1 = format.parse(newEndDate);
                                        Date date2 = format.parse(newCurrentDate);

                                        if (date1.compareTo(date2) > 0) {
                                            if (newCompleteTaskCount < newTaskCount) {
                                                goalActivityNumber = 1;
                                            } else if (newCompleteTaskCount == newTaskCount) {
                                                goalActivityNumber = 3;
                                            }
                                        } else if (date1.compareTo(date2) < 0) {
                                            if (newCompleteTaskCount < newTaskCount) {
                                                goalActivityNumber = 2;
                                            } else if (newCompleteTaskCount == newTaskCount) {
                                                goalActivityNumber = 3;
                                            }
                                        } else if (date1.compareTo(date2) == 0) {
                                            if (newCompleteTaskCount < newTaskCount) {
                                                goalActivityNumber = 1;
                                            } else if (newCompleteTaskCount == newTaskCount) {
                                                goalActivityNumber = 3;
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    adapter = new TaskAdapter(DisplayTaskScreen.this, cursor);
                                    taskListView.setAdapter(adapter);
                                    if (mGoalActivity == 1 && newTaskCount != 0){
                                        helper.updateGoal(goalId, newMaxDate, newMPercentage, goalActivityNumber, completeTaskCount, taskCount);
                                    }
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });
            } else {
                fab.setVisibility(View.GONE);
            }
            taskListView.setAdapter(adapter);
        } finally {
            db.close();
        }
        Animation anim_bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_to_top);
        Animation anim_left_to_right = AnimationUtils.loadAnimation(this, R.anim.anim_left_to_right);
        Animation anim_top_to_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_top_to_bottom);

        taskListView.setAnimation(anim_bottom_to_top);
        emptyTaskProgressBar.setAnimation(anim_left_to_right);
        taskProgressBar.setAnimation(anim_left_to_right);
        taskPercentage.setAnimation(anim_left_to_right);
        goalName.setAnimation(anim_top_to_bottom);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayTaskScreen.this, BottomNavigationViewActivity.class);
        intent.putExtra("goalId", goalId);
        startActivity(intent);
    }

    private Cursor updateUi() {

        db = helper.getReadableDatabase();

        String TaskAndGoalQuery = "SELECT g." + GoalContract.Goal_Name + ", "
                + "t." + TaskContract.Task_Name + ", "
                + "t." + TaskContract.Task_Date + ", "
                + "t." + TaskContract.Task_Notify_On + ", "
                + "t." + TaskContract.Task_Alarm + ", "
                + "t." + TaskContract.Task_NotifyState + ", "
                + "t." + TaskContract.Task_Goal_Id + ", "
                + "t." + TaskContract.Task_Id + ", "
                + "t." + TaskContract.Task_CheckBox_Completed
                + " FROM " + TaskContract.TABLE_NAME + " t "
                + " JOIN " + GoalContract.TABLE_NAME + " g "
                + " ON t." + TaskContract.Task_Goal_Id + " = " + goalId
                + " Where g." + GoalContract._ID + " = t." + TaskContract.Task_Goal_Id
                + " ORDER BY t." + TaskContract.Task_Date + ", t." + TaskContract.Task_Notify_On + " ASC ";

        Cursor cursor = db.rawQuery(TaskAndGoalQuery, null);

        return cursor;
    }
}
