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

import com.example.al_kahtani.sygoal.DisplayTaskScreen;
import com.example.al_kahtani.sygoal.GoalActivity;
import com.example.al_kahtani.sygoal.R;
import com.example.al_kahtani.sygoal.data.GoalAdapter;
import com.example.al_kahtani.sygoal.data.GoalContract;
import com.example.al_kahtani.sygoal.data.HelperClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CurrentGoalsFragment extends Fragment {

    ListView currentListView;
    View emptyView;

    GoalAdapter adapter;
    HelperClass helper;
    SQLiteDatabase db;

    String updateGoal = "0";
    String updateTask = "0";
    String currentDate;
    String storeDate;
    int selectedItem;
    int countedData = 0;
    int goalActivityNumber;
    private InterstitialAd mInterstitial;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadLocale();//load language setting

        View rootView = inflater.inflate(R.layout.current_goals_fragment, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(final View rootView, @Nullable Bundle savedInstanceState) {
        currentListView = rootView.findViewById(R.id.current_goal_list);
        emptyView = rootView.findViewById(R.id.empty_view);



        /// load the interstitial ads
        mInterstitial = new InterstitialAd(getContext());
        mInterstitial.setAdUnitId(getString(R.string.admob_publisher_interstitial_id));
        mInterstitial.loadAd(new AdRequest.Builder().build());

        helper = new HelperClass(rootView.getContext());

        try {
            //open Database to read info from it
            db = helper.getReadableDatabase();

            String[] projection = {GoalContract._ID,
                    GoalContract.Goal_Name,
                    GoalContract.Goal_Type,
                    GoalContract.Goal_Activity,
                    GoalContract.Goal_Complete_Count,
                    GoalContract.Goal_Complete_All,
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

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            month = month + 1;

            currentDate = year + "-" + month + "-" + day;

            while (mcursor.moveToNext()) {
                countedData = countedData + 1;
                int goalId = mcursor.getInt(mcursor.getColumnIndex(GoalContract._ID));
                int goalActivityNumber = mcursor.getInt(mcursor.getColumnIndex(GoalContract.Goal_Activity));
                storeDate = mcursor.getString(mcursor.getColumnIndex(GoalContract.Goal_MaxDate));
                if (goalActivityNumber == 1) {
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
                        Date date1 = format.parse(storeDate);
                        Date date2 = format.parse(currentDate);

                        if (date1.compareTo(date2) > 0) {
                            helper.updateGoalPlace(goalId, 1);
                        } else if (date1.compareTo(date2) < 0) {
                            helper.updateGoalPlace(goalId, 2);
                        } else if (date1.compareTo(date2) == 0) {
                            helper.updateGoalPlace(goalId, 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mcursor.close();

            if (countedData == 0) {
                final Cursor cursor = db.rawQuery(" Select * FROM " + GoalContract.TABLE_NAME, null);

                adapter = new GoalAdapter(rootView.getContext(), cursor);

                currentListView.setEmptyView(emptyView);

                currentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       final Intent i = new Intent(view.getContext(), DisplayTaskScreen.class);
                        i.putExtra("goalId", id);





                    }
                });

                currentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                    goalActivityNumber = 1;
                                    Intent intent = new Intent(rootView.getContext(), GoalActivity.class);
                                    intent.putExtra("goalId", id);
                                    intent.putExtra("updateGoal", updateGoal);
                                    intent.putExtra("goalActivity", goalActivityNumber);
                                    startActivity(intent);

                                } else if (selectedItem == R.id.delete) {
                                    helper.deleteGoal(id);
                                    Cursor cursor1 = updateUi();
                                    adapter = new GoalAdapter(getContext(), cursor1);
                                    currentListView.setAdapter(adapter);
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                        return true;
                    }
                });
                currentListView.setAdapter(adapter);
            }
            /**
             * --------------------------------------------------------------------
             * */
            else {
                final Cursor cursor = db.rawQuery(" Select * FROM " + GoalContract.TABLE_NAME + " WHERE "
                        + GoalContract.Goal_Activity + " = " + 1, null);

                adapter = new GoalAdapter(rootView.getContext(), cursor);

                currentListView.setEmptyView(emptyView);


                currentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       final Intent i = new Intent(view.getContext(), DisplayTaskScreen.class);
                        i.putExtra("goalId", id);

                        if (mInterstitial.isLoaded()){
                            mInterstitial.show();

                        }else{
                            startActivity(i);
                        }

                        mInterstitial.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {

                                // Load the next interstitial.
                                mInterstitial.loadAd(new AdRequest.Builder().build());
                                startActivity(i);
                            }




                        });



                    }
                });
                currentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view, int position, final long id) {

                        final PopupMenu popupMenu = new PopupMenu(rootView.getContext(), view);
                        popupMenu.inflate(R.menu.pop_up_menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                selectedItem = item.getItemId();
                                if (selectedItem == R.id.update) {
                                    updateGoal = "1";
                                    updateTask = "1";
                                    goalActivityNumber = 1;

                                    Intent intent = new Intent(rootView.getContext(), GoalActivity.class);
                                    intent.putExtra("goalId", id);
                                    intent.putExtra("updateGoal", updateGoal);
                                    intent.putExtra("goalActivity", goalActivityNumber);
                                    startActivity(intent);

                                } else if (selectedItem == R.id.delete) {
                                    helper.deleteGoal(id);
                                    Cursor cursor1 = updateUi();
                                    adapter = new GoalAdapter(getContext(), cursor1);
                                    currentListView.setAdapter(adapter);
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                        return true;
                    }
                });
                currentListView.setAdapter(adapter);
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

    private Cursor updateUi() {

        db = helper.getReadableDatabase();

        String[] projection = {GoalContract._ID,
                GoalContract.Goal_Name,
                GoalContract.Goal_Type,
                GoalContract.Goal_Activity,
                GoalContract.Goal_Complete_Count,
                GoalContract.Goal_Complete_All,
                GoalContract.Goal_Percentage,
                GoalContract.Goal_MaxDate,
                GoalContract.Goal_Description};

        final Cursor mcursor = db.query(GoalContract.TABLE_NAME,
                projection,
                GoalContract.Goal_Activity + "=?",
                new String[]{String.valueOf(1)},
                null,
                null,
                null,
                null);

        return mcursor;
    }
}
