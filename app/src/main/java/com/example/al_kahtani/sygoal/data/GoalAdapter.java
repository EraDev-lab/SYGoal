package com.example.al_kahtani.sygoal.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.al_kahtani.sygoal.R;

import java.util.ArrayList;

public class GoalAdapter extends CursorAdapter {

    HelperClass helper;
    SQLiteDatabase db;

    long goalId;
    int currentTaskGoalId;
    int currentTaskId;
    int allCount = 0;
    int doneCount = 0;
    double mPercentage = 0;
    String currentCheckBoxCompleted;

    public GoalAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.currrnt_goals_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        helper = new HelperClass(context);

            TextView name = view.findViewById(R.id.display_goal_name);
            name.setText(cursor.getString(cursor.getColumnIndex(GoalContract.Goal_Name)));

            ImageView type = view.findViewById(R.id.display_goal_type);

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


            TextView percentage = view.findViewById(R.id.display_goal_percentage);
            //ToDo: make a calculation to get the percentage.. from: Motowkel , to: Motwkel
            try {
                //open Database to read info from it
                db = helper.getReadableDatabase();
                goalId = cursor.getInt(cursor.getColumnIndex(GoalContract._ID));

                allCount = allCount + 1;

                mPercentage = Math.floor((doneCount * 100) / allCount);
                percentage.setText(mPercentage + "%");
                mPercentage = 0;
                allCount = 0;
                doneCount = 0;
            } finally {
                db.close();
        }
    }

}
