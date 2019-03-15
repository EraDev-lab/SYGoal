package com.example.al_kahtani.sygoal.data;

import android.provider.BaseColumns;

public class GoalContract implements BaseColumns {
    public static final String TABLE_NAME = "Goal";

    public static final String _ID = BaseColumns._ID;
    public static final String Goal_Activity = "goal_activity";
    public static final String Goal_Name = "goal_name";
    public static final String Goal_Type = "goal_type";
    public static final String Goal_Description = "description";


    public static final int TYPE_JOB = 1;
    public static final int TYPE_EXERCISE = 2;
    public static final int TYPE_HOUSE_WORK = 3;
    public static final int TYPE_SOCIAL = 4;
    public static final int TYPE_EDUCATION = 5;
    public static final int TYPE_OTHER = 6;


    private static int id;
    private String name;
    private String type;
    private String description;

    public GoalContract() {
    }

    public GoalContract(int id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public GoalContract(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public GoalContract(String name, String type) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public GoalContract(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public static int getGoalId() {
        return id;
    }

    public void setGoalId(int id) {
        this.id = id;
    }

    public String getGoalName() {
        return name;
    }

    public void setGoalName(String name) {
        this.name = name;
    }

    public String getGoalType() {
        if (type.equals(1)) {
            type = "Job";
        } else if (type.equals(2)) {
            type = "Exercise";
        } else if (type.equals(3)) {
            type = "House_Work";
        } else if (type.equals(4)) {
            type = "Social";
        } else if (type.equals(5)) {
            type = "Education";
        } else if (type.equals(6)) {
            type = "Other";
        } else {
            type = "TYPE_OTHER";
        }


        return type;
    }

    public void setGoalType(String type) {
        this.type = type;
    }


    public String getGoalDescription() {
        return description;
    }

    public void setGoalDescription(String description) {
        this.description = description;
    }

}

