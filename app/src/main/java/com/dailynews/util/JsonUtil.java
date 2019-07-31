package com.dailynews.util;

import com.dailynews.gsonModel.DetailedStory;
import com.dailynews.gsonModel.NewsList;
import com.google.gson.Gson;

public class JsonUtil {
    public static NewsList handleNewsListResponse(String data){
        return new Gson().fromJson(data,NewsList.class);
    }
    public static DetailedStory handleDetailedStory(String data){
        return new Gson().fromJson(data,DetailedStory.class);
    }
}
