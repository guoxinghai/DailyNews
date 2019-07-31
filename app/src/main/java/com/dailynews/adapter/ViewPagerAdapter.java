package com.dailynews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dailynews.R;
import com.dailynews.gsonModel.BriefStory;
import com.dailynews.gsonModel.BriefTopStory;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<BriefTopStory> mData;

    public ViewPagerAdapter(Context context ,List<BriefTopStory> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BriefTopStory briefTopStory = mData.get(position);
        View view = View.inflate(mContext, R.layout.topnews,null);
        ImageView imageView = view.findViewById(R.id.topNews_image);
        TextView textView = view.findViewById(R.id.topNews_title);
        textView.setText(briefTopStory.getTitle());
        Glide.with(mContext).load(briefTopStory.getImage()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

