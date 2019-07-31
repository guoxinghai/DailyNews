package com.dailynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dailynews.MainActivity;
import com.dailynews.NewsDetailedActivity;
import com.dailynews.R;
import com.dailynews.gsonModel.BriefStory;
import com.dailynews.gsonModel.BriefTopStory;
import com.dailynews.gsonModel.DetailedStory;
import com.dailynews.util.HttpsUtil;
import com.dailynews.util.JsonUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
        private String address="https://news-at.zhihu.com/api/4/news/";
        //用来存取需要加载的数据的集合对象 这份代码的集合用于存储消息对象
        private List<BriefStory> dataList;
        //构造方法只需将数据集合传递进来即可
        public RecyclerViewAdapter(List<BriefStory> messages){
            this.dataList = messages;
        }

        //onCreateViewHolder()函数是用来创建ViewHolder对象的（关于ViewHolder会在下面介绍）
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newslist,viewGroup,false);
            return new ViewHolder(view);
        }


    //onBindViewHolder()函数是用来将数据绑定到ViewHolder中的属性的，也可以在这为部件设置监听等事件
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int i) {
            final BriefStory briefStory = dataList.get(i);
            final ViewHolder vh = (ViewHolder)viewHolder;
            vh.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(v.getContext(),briefStory);
                }
            });
            vh.textView.setText(briefStory.getTitle());
            ImageView imageView = vh.imageView;
            if(briefStory.getImages().length>0){
                Glide.with(imageView.getContext()).load(briefStory.getImages()[0]).into(imageView);
            }
        }

        //getItemCount()获取集合对象的大小
        @Override
        public int getItemCount() {
            return dataList.size();
        }

        /*
         * 下面是ViewHolder类继承自RecyclerView.ViewHolder类,
         * ViewHolder用于存储与某个视图对象有关的数据
         * 使用View.setTag()来设置ViewHolder
         * 使用View.getTag()来获取ViewHolder
         * 这里使用ViewHolder是为了减少View.findViewById()方法的使用
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;
            View itemView;
            //构造函数获取一个itemView即recyclerView的布局子对象
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.itemView = itemView;
                textView = (TextView)itemView.findViewById(R.id.news_title);
                imageView = (ImageView) itemView.findViewById(R.id.news_image);
            }
        }
            //启动NewsDetailedActivity
            private void startActivity(final Context context, BriefStory story){
                String addr = address+story.getId();
                HttpsUtil.sendOkHttpRequest(addr, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(context,"加载新闻失败",Toast.LENGTH_SHORT);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        DetailedStory detailedStory = JsonUtil.handleDetailedStory(data);
                        Intent intent = new Intent(context,NewsDetailedActivity.class);
                        intent.putExtra("body",detailedStory.getBody());
                        intent.putExtra("image_source",detailedStory.getImage_source());
                        intent.putExtra("title",detailedStory.getTitle());
                        intent.putExtra("image",detailedStory.getImage());
                        if(detailedStory.getJs().length>0){
                            intent.putExtra("js",detailedStory.getJs()[0]);
                        }
                        if(detailedStory.getCss().length>0){
                            intent.putExtra("string",detailedStory.getCss());
                        }
                        context.startActivity(intent);
                    }
                });
            }
    }
