package com.example.al_kahtani.sygoal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al_kahtani.sygoal.data.GoalContract;
import com.example.al_kahtani.sygoal.data.HelperClass;

public class AchieveAndMissedAdapter extends CursorAdapter {
    HelperClass helperClass;
    SQLiteDatabase db;

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
      }
}