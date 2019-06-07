package com.example.al_kahtani.sygoal.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.al_kahtani.sygoal.AchieveAndMissedAdapter;
import com.example.al_kahtani.sygoal.DisplayTaskScreen;
import com.example.al_kahtani.sygoal.R;
import com.example.al_kahtani.sygoal.data.GoalContract;
import com.example.al_kahtani.sygoal.data.HelperClass;

import java.util.Locale;

public class Achievement_fragment extends Fragment {

    ListView achieveListView;
    ImageView emptyView;

    String updateGoal;
    String updateTask = "0";
    int selectedItem;
    int countedData = 0;
    int goalActivityNumber;

    HelperClass helper;
    AchieveAndMissedAdapter adapter;
    SQLiteDatabase db;

    public Achievement_fragment() {
        // Required emptyView public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadLocale();//load languge setting
        final View view = inflater.inflate(R.layout.achievements_fragment, container, false);

        // Find the {@link ListView} object in the view hierarchy of the {@link achievements_fragment}.
        // There should be a {@link ListView} with the view ID called list_view_achievement.
        achieveListView = view.findViewById(R.id.list_view_achievement);
        emptyView = view.findViewById(R.id.empty_view);
        return view;
    }

    @Override
    public void onViewCreated(final View rootView, @Nullable Bundle savedInstanceState) {
        //instantiate object from HelperClass to access to it method
        helper = new HelperClass(rootView.getContext());

        try {
            //open Database to read info from it
            db = helper.getReadableDatabase();

            String[] projection = {GoalContract._ID,
                    GoalContract.Goal_Name,
                    GoalContract.Goal_Type,
                    GoalContract.Goal_Activity,
                    GoalContract.Goal_Percentage,
                    GoalContract.Goal_MaxDate,
                    GoalContract.Goal_Description};

            final Cursor mcursor = db.query(GoalContract.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

            while (mcursor.moveToNext()) {
                countedData = countedData + 1;
            }
            mcursor.close();

            if (countedData == 0) {
                final Cursor cursor = db.rawQuery(" SELECT * FROM " + GoalContract.TABLE_NAME, null);

                adapter = new AchieveAndMissedAdapter(rootView.getContext(), cursor);
                achieveListView.setEmptyView(emptyView);

                achieveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(view.getContext(), DisplayTaskScreen.class);
                        i.putExtra("goalId", id);
                        startActivity(i);
                    }
                });
                achieveListView.setAdapter(adapter);
            }
            /**
             * --------------------------------------------------------------------
             * */
            else {

                String myQuery = " SELECT * FROM " + GoalContract.TABLE_NAME
                        + " WHERE " + GoalContract.Goal_Activity + " = " + 3;
                //+ "WHERE (SELECT" + " MAX(t." + TaskContract.Task_Date + ") < " + "date('now')"
                //+ "OR MAX(t." + TaskContract.Task_Date + ") = " + "date('now'))";

                final Cursor cursor = db.rawQuery(myQuery, null);

                adapter = new AchieveAndMissedAdapter(rootView.getContext(), cursor);
                achieveListView.setEmptyView(emptyView);

                achieveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(view.getContext(), DisplayTaskScreen.class);
                        i.putExtra("goalId", id);
                        startActivity(i);
                    }
                });
                achieveListView.setAdapter(adapter);
            }
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
        getActivity().getResources().updateConfiguration(configuration, getActivity().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("SettingActivity", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences pref = getActivity().getSharedPreferences("SettingActivity", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }
}
