package com.example.al_kahtani.sygoal;

public class GoalClass {
    /**
     * Default title name of the goal
     */
    private String Title;
    /**
     * Default photo of the goal
     */
    private int Thumbnail;

    private String mCurrent_goal_name;
    private String mRate;
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new goal object.
     *
     * @param Current_goal_name
     * @param Rate
     */
    public GoalClass(String Current_goal_name, String Rate) {
        mCurrent_goal_name = Current_goal_name;
        mRate = Rate;
    }

    /**
     * @param title     is a name of goal
     * @param thumbnail is a photo of goal
     */
    public GoalClass(String title, int thumbnail) {
        Title = title;
        Thumbnail = thumbnail;
    }

    /*
     * Get the Default title name of the goal.
     */
    public String getTitle() {
        return Title;
    }

    /*
     * Get the Default photo of the goal.
     */
    public int getThumbnail() {
        return Thumbnail;
    }

    /*
     * Set the Default title name of the goal.
     */
    public void setTitle(String title) {
        Title = title;
    }

    /*
     * Set the Default photo of the goal.
     */
    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
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
