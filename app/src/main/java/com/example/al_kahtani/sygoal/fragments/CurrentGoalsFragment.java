package com.example.al_kahtani.sygoal.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.al_kahtani.sygoal.R;

import java.util.ArrayList;

public class CurrentGoalsFragment extends Fragment {
    ListView listViewcurrent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = getLayoutInflater().inflate(R.layout.current_goals_fragment, container, false);
        listViewcurrent = (ListView) rootView.findViewById(R.id.current_goals_lv);


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<GoalClass> titles = new ArrayList<>();
        titles.add(new GoalClass("brown", "12%"));
        titles.add(new GoalClass("gray", "20%"));
        titles.add(new GoalClass("black", "2%"));
        titles.add(new GoalClass("white", "66%"));
        titles.add(new GoalClass("red", "98%"));


        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_missed_goals);
      icons.add(R.drawable.housework);
        icons.add(R.drawable.ic_achievements);
        icons.add(R.drawable.job);

        currentAdapter adapter = new currentAdapter(getActivity(), titles);

      listViewcurrent.setAdapter(adapter);

        listViewcurrent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
}
    }
