package com.example.al_kahtani.sygoal.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.al_kahtani.sygoal.CurrentAdapter;
import com.example.al_kahtani.sygoal.GoalClass;
import com.example.al_kahtani.sygoal.R;
import com.example.al_kahtani.sygoal.Setting;
import com.example.al_kahtani.sygoal.TaskActivity;

import java.util.ArrayList;

public class CurrentGoalsFragment extends Fragment {
    ListView listViewcurrent;
    TextView name_current;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = getLayoutInflater().inflate(R.layout.current_goals_fragment,container,false);
//////////////////////////

        listViewcurrent =  rootView.findViewById(R.id.current_goals_lv);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final ArrayList<GoalClass> titles = new ArrayList<>();
        titles.add(new GoalClass("Task1", "12%"));
        titles.add(new GoalClass("Task2", "20%"));
        titles.add(new GoalClass("Task3", "2%"));
        titles.add(new GoalClass("Task4", "66"));
        titles.add(new GoalClass("Task5", "98"));


        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_missed_goals);
      icons.add(R.drawable.housework);
        icons.add(R.drawable.ic_achievements);
        icons.add(R.drawable.job);

        CurrentAdapter adapter = new CurrentAdapter(getActivity(), titles);

      listViewcurrent.setAdapter(adapter);

        listViewcurrent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                GoalClass title= titles.get(index);
                name_current.setText(title.getCurrent_goal_name());;
                return false;
            }
        });
    }
    }
