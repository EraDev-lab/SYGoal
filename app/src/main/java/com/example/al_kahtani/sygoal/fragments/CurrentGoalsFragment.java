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
import android.widget.TextView;

import com.example.al_kahtani.sygoal.R;
import com.example.al_kahtani.sygoal.Setting;
import com.example.al_kahtani.sygoal.TaskActivity;

public class CurrentGoalsFragment extends Fragment {
    private TextView saveBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = getLayoutInflater().inflate(R.layout.current_goals_fragment,container,false);
//////////////////////////
        saveBtn =  rootView.findViewById(R.id.current_goals_desc);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), Setting.class);
                startActivity(it);
            }
        });///////////////////////////---------------------
        return rootView;
    }
}
