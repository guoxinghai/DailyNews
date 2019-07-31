package com.dailynews.gsonModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsList {
    private String date;
    @SerializedName("stories")
    private List<BriefStory> briefStories;
    @SerializedName("top_stories")
    private List<BriefTopStory> briefTopStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BriefStory> getBriefStories() {
        return briefStories;
    }

    public void setBriefStories(List<BriefStory> briefStories) {
        this.briefStories = briefStories;
    }

    public List<BriefTopStory> getBriefTopStories() {
        return briefTopStories;
    }

    public void setBriefTopStories(List<BriefTopStory> briefTopStories) {
        this.briefTopStories = briefTopStories;
    }
}
