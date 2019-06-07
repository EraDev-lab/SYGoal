package com.example.al_kahtani.sygoal.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.al_kahtani.sygoal.R;

/**
 * We using CursorAdapter since it's better than the ArrayAdapter.
 * The CursorAdapter have Two Implements method:
 * bindView: get the data and display it on the screen.
 * newView: inflate the xml Layout to display the data on ot.
 */
public class GoalAdapter extends CursorAdapter {

    //Constructor
    public GoalAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    //inflate the xml Layout to display the data on ot.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.currrnt_goals_item, parent, false);
    }

    //get the data and display it on the screen.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //display Goal Name
        TextView name = view.findViewById(R.id.display_goal_name);
        name.setText(cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Name)));

        //display Goal Type
        ImageView type = view.findViewById(R.id.display_goal_type);

        //adjust some operation to display the correct Goal Type.
        int typeColumnIndex = cursor.getColumnIndex(GoalContract.Goal_Type);
        int mType = cursor.getInt(typeColumnIndex);
        if (mType == 1) {
            type.setImageResource(R.drawable.job);
        } else if (mType == 2) {
            type.setImageResource(R.drawable.housework);
        } else if (mType == 3) {
            type.setImageResource(R.drawable.education);
        } else if (mType == 4) {
            type.setImageResource(R.drawable.exercise);
        } else if (mType == 5) {
            type.setImageResource(R.drawable.social);
        } else if (mType == 6) {
            type.setImageResource(R.drawable.other);
        } else
            type.setImageResource(R.drawable.other);

        //display the percentage
        TextView percentage = view.findViewById(R.id.display_goal_percentage);
        percentage.setText((int) cursor.getDouble(cursor.getColumnIndex(GoalContract.Goal_Percentage)) + "%");

        //display the percentage on ProgressBar
        ProgressBar percentageOnProgress = view.findViewById(R.id.current_goal_progressbar);
        percentageOnProgress.setProgress((int) cursor.getDouble(cursor.getColumnIndex(GoalContract.Goal_Percentage)));
    }
}
