package com.example.al_kahtani.sygoal.data;

import android.provider.BaseColumns;

public class GoalContract implements BaseColumns {

    //Goal Table Name
    public static final String TABLE_NAME = "Goal";

    //Goal Columns
    public static final String _ID = BaseColumns._ID;
    public static final String Goal_Activity = "goal_activity";
    public static final String Goal_Name = "goal_name";
    public static final String Goal_Type = "goal_type";
    public static final String Goal_MaxDate = "goal_max_date";
    public static final String Goal_Percentage = "goal_percentage";
    public static final String Goal_Complete_All = "goal_complete_all";
    public static final String Goal_Complete_Count = "goal_complete_count";
    public static final String Goal_Description = "description";

    //constructor
    public GoalContract() {
    }
}
