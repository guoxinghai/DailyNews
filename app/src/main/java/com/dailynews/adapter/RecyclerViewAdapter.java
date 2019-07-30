package com.dailynews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dailynews.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
        //用来存取需要加载的数据的集合对象 这份代码的集合用于存储消息对象
        List<String> dataList;
        //构造方法只需将数据集合传递进来即可
        public RecyclerViewAdapter(List<String> messages){
            this.dataList = messages;
        }

        //onCreateViewHolder()函数是用来创建ViewHolder对象的（关于ViewHolder会在下面介绍）
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news,viewGroup,false);
            return new ViewHolder(view);
        }


    //onBindViewHolder()函数是用来将数据绑定到ViewHolder中的属性的，也可以在这为部件设置监听等事件
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int i) {
            final String message = dataList.get(i);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),message,Toast.LENGTH_SHORT).show();
                }
            });
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
            //构造函数获取一个itemView即cyclerView的布局子对象
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.itemView = itemView;
                textView = (TextView)itemView.findViewById(R.id.news_title);
                imageView = (ImageView) itemView.findViewById(R.id.news_image);
            }
        }
    }
