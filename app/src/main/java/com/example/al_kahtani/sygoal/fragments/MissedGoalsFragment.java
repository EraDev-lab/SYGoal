package com.example.al_kahtani.sygoal.fragments;

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

public class MissedGoalsFragment extends Fragment {

    List<GoalClass> lstGoal ;

    public MissedGoalsFragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.missed_goals_fragments, container, false);

        RecyclerView myrv = (RecyclerView) view.findViewById(R.id.recyclerViewMissed);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(),lstGoal);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        myrv.setLayoutManager(gridLayoutManager);
        myrv.setAdapter(myAdapter);
        return view;
    }
}
