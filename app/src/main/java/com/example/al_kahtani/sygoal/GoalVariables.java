package com.example.al_kahtani.sygoal;

public class GoalVariables {

    private String Title;
    private int Thumbnail;

    public GoalVariables() {
    }

    public GoalVariables(String title, int thumbnail) {
        Title = title;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
