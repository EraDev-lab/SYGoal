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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.al_kahtani.sygoal.AcheiveAdapter;
import com.example.al_kahtani.sygoal.DisplayTaskScreen;
import com.example.al_kahtani.sygoal.GoalActivity;
import com.example.al_kahtani.sygoal.R;
import com.example.al_kahtani.sygoal.data.GoalContract;
import com.example.al_kahtani.sygoal.data.HelperClass;

import java.util.Locale;

public class MissedGoalsFragment extends Fragment {

    ListView lv;

    String updateGoal;
    String updateTask = "0";
    int selectedItem;
    int i = 0;

    HelperClass helper;
    AcheiveAdapter myAdapter;
    SQLiteDatabase db;

    public MissedGoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadLocale();//load languge setting
        final View view = inflater.inflate(R.layout.missed_goals_fragments, container, false);

        // Find the {@link RecyclerView} object in the view hierarchy of the {@link achievements_fragment}.
        // There should be a {@link RecyclerView} with the view ID called recyclerViewAchi.
        lv = view.findViewById(R.id.list_view_missed);

        return view;
    }

    @Override
    public void onViewCreated(final View rootView, @Nullable Bundle savedInstanceState) {

        helper = new HelperClass(rootView.getContext());

        try {
            //open Database to read info from it
            db = helper.getReadableDatabase();

            String[] projection = {
                    GoalContract._ID,
                    GoalContract.Goal_Name,
                    GoalContract.Goal_Type,
                    GoalContract.Goal_Description
            };
            final Cursor mcursor = db.rawQuery(" Select * FROM " + GoalContract.TABLE_NAME, null);

            if (mcursor.moveToFirst()) {
                while (mcursor.moveToNext()) {
                    i = i + 1;
                }
                db.close();
                mcursor.close();
            }
            db = helper.getReadableDatabase();
            if (i == 0) {
                final Cursor cursor = db.rawQuery(" Select * FROM " + GoalContract.TABLE_NAME, null);

                myAdapter = new AcheiveAdapter(rootView.getContext(), cursor);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(view.getContext(), DisplayTaskScreen.class);
                        i.putExtra("goalId", id);
                        startActivity(i);
                    }
                });
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {

                        final PopupMenu popupMenu = new PopupMenu(rootView.getContext(), view);
                        popupMenu.inflate(R.menu.pop_up_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                selectedItem = item.getItemId();
                                if (selectedItem == R.id.update) {
                                    updateGoal = "1";
                                    updateTask = "1";
                                    Intent intent = new Intent(rootView.getContext(), GoalActivity.class);
                                    intent.putExtra("goalId", id);
                                    intent.putExtra("updateGoal", updateGoal);
                                    startActivity(intent);

                                } else if (selectedItem == R.id.delete) {
                                    helper.deleteGoal(id);
                                    Intent intent = new Intent(view.getContext(), view.getContext().getClass());
                                    intent.putExtra("goalId", id);
                                    startActivity(intent);

                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                        return true;
                    }
                });
                lv.setAdapter(myAdapter);
            }
            /**
             * --------------------------------------------------------------------
             * */
            else {

                final Cursor cursor = db.rawQuery(" Select * FROM " + GoalContract.TABLE_NAME + " WHERE "
                        + GoalContract.Goal_Activity + " = " + 2 , null);

                myAdapter = new AcheiveAdapter(rootView.getContext(), cursor);



                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(view.getContext(), DisplayTaskScreen.class);
                        i.putExtra("goalId", id);
                        startActivity(i);
                    }
                });
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {

                        final PopupMenu popupMenu = new PopupMenu(rootView.getContext(), view);
                        popupMenu.inflate(R.menu.pop_up_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                selectedItem = item.getItemId();
                                if (selectedItem == R.id.update) {
                                    updateGoal = "1";
                                    updateTask = "1";
                                    Intent intent = new Intent(rootView.getContext(), GoalActivity.class);
                                    intent.putExtra("goalId", id);
                                    intent.putExtra("updateGoal", updateGoal);
                                    startActivity(intent);

                                } else if (selectedItem == R.id.delete) {
                                    helper.deleteGoal(id);
                                    Intent intent = new Intent(view.getContext(), view.getContext().getClass());
                                    intent.putExtra("goalId", id);
                                    startActivity(intent);

                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                        return true;
                    }
                });
                lv.setAdapter(myAdapter);
            }
        }
        finally {
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
