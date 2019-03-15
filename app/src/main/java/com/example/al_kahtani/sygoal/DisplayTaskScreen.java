package com.example.al_kahtani.sygoal;

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
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.al_kahtani.sygoal.classes.SharedPref;
import com.example.al_kahtani.sygoal.data.GoalContract;
import com.example.al_kahtani.sygoal.data.HelperClass;
import com.example.al_kahtani.sygoal.data.TaskAdapter;
import com.example.al_kahtani.sygoal.data.TaskContract;
import com.example.al_kahtani.sygoal.data.TaskGoalContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DisplayTaskScreen extends AppCompatActivity {

    FloatingActionButton fab;
    ListView taskListView;
    TextView goalName;

    long goalId;
    String updateTask;
    String max;

    SharedPref sharedpref;
    HelperClass helper;
    TaskAdapter adapter;
    SQLiteDatabase db;
    Calendar calendar;

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
        goalName = findViewById(R.id.clean);

        Intent intent = getIntent();
        goalId = intent.getLongExtra("goalId", goalId);

        helper = new HelperClass(this);

        try {
            //open Database to read info from it
            db = helper.getReadableDatabase();

            final String Task_And_Goal = "SELECT g." + GoalContract.Goal_Name + ", "
                    + "t." + TaskContract.Task_Name + ", "
                    + "t." + TaskContract.Task_Id + ", "
                    + "t." + TaskContract.Task_Date + ", "
                    + "t." + TaskContract.Task_Notify_On + ", "
                    + "t." + TaskContract.Task_Alarm + ", "
                    + "t." + TaskContract.Task_CheckBox_Completed
                    + " FROM " + GoalContract.TABLE_NAME + " g "
                    + " LEFT JOIN " + TaskContract.TABLE_NAME + " t "
                    + " ON " + " t." + TaskContract.Task_Goal_Id + " = " + goalId
                    + " Where g." + GoalContract._ID + " = t." + TaskContract.Task_Goal_Id
                    + " ORDER BY " + TaskContract.Task_Date + ", " + TaskContract.Task_Notify_On + " ASC " ;

            final Cursor cursor = db.rawQuery(Task_And_Goal, null);

            adapter = new TaskAdapter(this, cursor);

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
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(DisplayTaskScreen.this, "clicked", Toast.LENGTH_SHORT).show();
                }
            });

            taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
                    final PopupMenu popupMenu = new PopupMenu(DisplayTaskScreen.this, view);
                    popupMenu.inflate(R.menu.pop_up_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int selectedItem = item.getItemId();
                            if (selectedItem == R.id.update) {
                                updateTask = "1";
                                Intent intent = new Intent(DisplayTaskScreen.this, TaskActivity.class);
                                //ToDo: you may need to make the goalId of type String..from: motwkel , to: motwkel
                                intent.putExtra("taskId", id);
                                intent.putExtra("updateTask", updateTask);
                                startActivity(intent);

                            } else if (selectedItem == R.id.delete) {
                                helper.deleteTask(id);
                                Intent intent = new Intent(DisplayTaskScreen.this, DisplayTaskScreen.class);
                                intent.putExtra("goalId", goalId);
                                startActivity(intent);

                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
            taskListView.setAdapter(adapter);
        } finally {
            db.close();
        }
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

}
