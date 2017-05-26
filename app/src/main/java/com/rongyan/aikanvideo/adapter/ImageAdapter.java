package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.util.List;

/**
 * Created by XRY on 2017/4/24.
 */

public class ImageAdapter extends PagerAdapter {
    private static final String TAG = "ImageAdapter";

    List<ImageView> list;
    private Context context;
    private List<Video> videoList;

    private int mCount = 0;

    public ImageAdapter(List<ImageView> list, Context context) {
        this.list = list;
        this.context = context;
        LogUtils.d(TAG, "ImageAdapter", "constructor");
    }

    @Override
    public int getCount() {
        //设置成最大，用户看不到边界
        LogUtils.d(TAG, "ImageAdapter", "constructor");
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemPosition(Object object) {
        if (mCount > 0) {
            mCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        LogUtils.d(TAG, "ImageAdapter", "isViewFromObject:" + (view == object));
        return view == object;
    }

    @Override
    public void notifyDataSetChanged() {
        mCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtils.d(TAG, "ImageAdapter", "destroyItem");
        //super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //重新计算位置
        position %= list.size();
        if (position < 0) {
            position = list.size() + position;

        }
        LogUtils.d(TAG, "instantiateItem", position + "");
        ImageView imageView = list.get(position);
        //imageView.setImageResource(R.mipmap.ic_launcher);
        ViewParent parent = imageView.getParent();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //如果View在之前已经添加到一个父组件，必须先remove，否则抛出IllegalStateException
        if (parent != null) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.removeView(imageView);
        }
        container.addView(imageView);

        return imageView;
    }

    public void replaceList(List<Video> list, List<ImageView> imgList) {
        this.list.clear();
        this.list = imgList;
        this.videoList = list;
        notifyDataSetChanged();

    }
}
