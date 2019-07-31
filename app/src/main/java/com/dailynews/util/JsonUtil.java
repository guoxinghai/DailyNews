package com.dailynews.util;

import com.dailynews.gsonModel.NewsList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static NewsList handleNewsListResponse(String data){
        return new Gson().fromJson(data,NewsList.class);
    }
}
