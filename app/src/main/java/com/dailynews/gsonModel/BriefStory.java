package com.dailynews.gsonModel;

import com.google.gson.annotations.SerializedName;

public class BriefStory {
    @SerializedName("images")
    private String[] images;
    private int id;
    private String title;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
