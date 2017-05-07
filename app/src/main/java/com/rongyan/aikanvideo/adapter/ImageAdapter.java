package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by XRY on 2017/4/24.
 */

public class ImageAdapter extends PagerAdapter {
    ArrayList<ImageView> list;
    private Context context;

    public ImageAdapter(ArrayList<ImageView> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        //设置成最大，用户看不到边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //重新计算位置
        position %= list.size();
        if (position < 0) {
            position = list.size() + position;
        }
        ImageView imageView = list.get(position);
        ViewParent parent = imageView.getParent();
        //如果View在之前已经添加到一个父组件，必须先remove，否则抛出IllegalStateException
        if (parent != null) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }
}
