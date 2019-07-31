package com.dailynews;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.dailynews.Decoration.SpacesItemDecoration;
import com.dailynews.adapter.RecyclerViewAdapter;
import com.dailynews.adapter.ViewPagerAdapter;
import com.dailynews.gsonModel.BriefStory;
import com.dailynews.gsonModel.BriefTopStory;
import com.dailynews.gsonModel.NewsList;
import com.dailynews.util.HttpsUtil;
import com.dailynews.util.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
//news为在sharedPreferences中的缓存
public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private CircleIndicator circleIndicator;
    private final String newsListAddress="https://news-at.zhihu.com/api/4/news/latest";
    private List<BriefStory> briefStories = new ArrayList<>();
    private List<BriefTopStory> briefTopStories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置viewPager
        viewPagerAdapter = new ViewPagerAdapter(this,briefTopStories);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        //为viewPager设置小圆点
        circleIndicator = (CircleIndicator)findViewById(R.id.circleindicator);
        circleIndicator.setViewPager(viewPager);
        //设置recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(briefStories);
        //RecyclerView设置item间距
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(30);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(spacesItemDecoration);
        //设置下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestByServer();
            }
        });
        //将状态栏和背景融为一体
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            //将活动得布局显示在状态栏上面
            decorView.setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //将状态栏设置成透明的
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //请求新闻列表
        requestNewsList();

    }
    //请求并解析新闻列表(优先使用缓存中的内容)
    private void requestNewsList(){
        //判断缓存
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String data = sharedPreferences.getString("newslist",null);
        if(data!=null){
            setNewsList(data);
        }else{
            requestByServer();
        }

    }
    //直接通过发送请求获取新闻内容
    private void requestByServer(){
        HttpsUtil.sendOkHttpRequest(newsListAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"请求新闻列表失败",Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                //将data置入缓存
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("newslist",data);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setNewsList(data);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    //设置新闻列表
    private void setNewsList(String data){
        NewsList newsList = JsonUtil.handleNewsListResponse(data);
        setStoriesList(newsList.getBriefStories());
        setTopStoriesList(newsList.getBriefTopStories());
    }
    //设置stories列表
    private void setStoriesList(List<BriefStory> briefStoryList){
        if(briefStoryList!=null&&briefStoryList.size()>0){
            this.briefStories.clear();
            this.briefStories.addAll(briefStoryList);
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        }
    }
    //设置topStories列表
    private void setTopStoriesList(List<BriefTopStory> briefTopStoryList){
        if(briefTopStoryList!=null&&briefTopStoryList.size()>0){
            this.briefTopStories.clear();
            this.briefTopStories.addAll(briefTopStoryList);
            viewPagerAdapter.notifyDataSetChanged();
            circleIndicator.removeAllViews();
            circleIndicator.setViewPager(viewPager);
        }
    }
}
