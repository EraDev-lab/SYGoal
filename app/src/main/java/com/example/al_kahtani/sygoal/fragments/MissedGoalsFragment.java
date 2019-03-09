package com.example.al_kahtani.sygoal.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.al_kahtani.sygoal.GoalClass;
import com.example.al_kahtani.sygoal.R;
import com.example.al_kahtani.sygoal.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MissedGoalsFragment extends Fragment {

    List<GoalClass> lstGoal ;

    public MissedGoalsFragment() {
        // Required empty public constructor
    }

    /**
     * // Create a list of Missed Goals with its name and photo
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstGoal = new ArrayList<>();
        lstGoal.add(new GoalClass("Education",R.drawable.education));
        lstGoal.add(new GoalClass("Exercise",R.drawable.exercise));
        lstGoal.add(new GoalClass("House Cleaning ",R.drawable.homee));
        lstGoal.add(new GoalClass("Meeting",R.drawable.meeting));
        lstGoal.add(new GoalClass("Education",R.drawable.education));
        lstGoal.add(new GoalClass("Exercise",R.drawable.exercise));
        lstGoal.add(new GoalClass("House Cleaning ",R.drawable.homee));
        lstGoal.add(new GoalClass("Meeting",R.drawable.meeting));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadLocale();

        View view = inflater.inflate(R.layout.missed_goals_fragments, container, false);

        // Find the {@link RecyclerView} object in the view hierarchy of the {@link missed_goals_fragments}.
        // There should be a {@link RecyclerView} with the view ID called recyclerViewMissed.
        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerViewMissed);
        //sure that the size of the RecyclerView won't be changing
        myrv.setHasFixedSize(true);

        // Create an {@link RecyclerViewAdapter}, whose data source is a list of {@link Goal}s. The
        // adapter knows how to create Recycler View items for each item in the list.
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(),lstGoal);

        //This LayoutManager subclass will, by default, make the RecyclerView look like a ListView.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        myrv.setLayoutManager(gridLayoutManager);

        // Make the {@link RecyclerView} use the {@link RecyclerViewAdapter} we created above, so that the
        // {@link RecyclerView} will display Recycler View items for each {@link Goal} in the list.
        myrv.setAdapter(myAdapter);
        return view;
    }
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getResources().updateConfiguration(configuration, getActivity().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences pref = getActivity().getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }
}
