package com.example.al_kahtani.sygoal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrentAdapter extends ArrayAdapter<GoalClass> {


    public CurrentAdapter(Context context, ArrayList<GoalClass> titles) {
        super(context, 0, titles);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.currrnt_goals_item, parent, false);
        }
        GoalClass currentWord = getItem(position);
        TextView current_goal = listItemView.findViewById(R.id.current_goal_tv);
        current_goal.setText(currentWord.getCurrent_goal_name());
        TextView current_goal_Rate =  listItemView.findViewById(R.id.current_goal_pers_tv);
        current_goal_Rate.setText(currentWord.getRate());
        ImageView imageView = listItemView.findViewById(R.id.current_goal_image);

        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());

            imageView.setVisibility(View.VISIBLE);
        } else {

            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }

}
