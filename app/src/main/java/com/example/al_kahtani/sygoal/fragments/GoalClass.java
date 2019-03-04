package com.example.al_kahtani.sygoal.fragments;

class GoalClass {

    private String mCurrent_goal_name;
    private String mRate;
    private static final int NO_IMAGE_PROVIDED = -1;
  public GoalClass(String Current_goal_name, String Rate) {
      mCurrent_goal_name=Current_goal_name;
      mRate=Rate;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }


    private int mImageResourceId = NO_IMAGE_PROVIDED;

    public String getCurrent_goal_name() {
        return mCurrent_goal_name;
    }

    public void setCurrent_goal_name(String current_goal_name) {
        mCurrent_goal_name = current_goal_name;
    }


    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
    public int getImageResourceId() {
        return mImageResourceId;
    }

}
