package com.daily.reach.sygoal;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daily.reach.sygoal.data.GoalContract;

public class AchieveAndMissedAdapter extends CursorAdapter {

    int goal_complete_count = 0;
    int goal_complete_all = 0;

    public AchieveAndMissedAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.achieve_and_missed_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = view.findViewById(R.id.our_goal_name);
        name.setText(cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Name)));

        ImageView type = view.findViewById(R.id.our_goal_type);

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

        TextView description = view.findViewById(R.id.our_goal_description);
        description.setText(cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Description)));

        TextView complete_number = (TextView) view.findViewById(R.id.complete_number);
        goal_complete_count = cursor.getInt(cursor.getColumnIndex(GoalContract.Goal_Complete_Count));
        goal_complete_all = cursor.getInt(cursor.getColumnIndex(GoalContract.Goal_Complete_All));

        complete_number.setText(goal_complete_count + "/" + goal_complete_all + "");
    }
}