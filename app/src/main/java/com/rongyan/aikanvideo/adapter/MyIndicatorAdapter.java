package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.main.MainActivity;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

/**
 * Created by XRY on 2017/4/24.
 */

public class MyIndicatorAdapter extends CommonNavigatorAdapter {
    String[] titles = new String[]{"首页", "发现", "订阅"};
    private MainActivity activity;
    private ViewPager viewPager;

    public MyIndicatorAdapter(MainActivity activity, ViewPager viewPager) {
        this.activity = activity;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public IPagerTitleView getTitleView(final Context context, final int i) {
        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        commonPagerTitleView.setContentView(R.layout.pager_item);
        final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.pager_img);
        final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.pager_text);
        textView.setText(titles[i]);
        switch (i) {
            case 0:
                imageView.setImageResource(R.mipmap.home);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.found);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.subscribe);
                break;
        }
        commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(i);
            }
        });
        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
            @Override
            public void onSelected(int i, int i1) {
                textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                activity.toolbar.setTitle(titles[i]);
                //activity.setSupportActionBar(activity.toolbar);
            }

            @Override
            public void onDeselected(int i, int i1) {
                textView.setTextColor(context.getResources().getColor(R.color.colorTextGray));
            }

            @Override
            public void onLeave(int i, int i1, float v, boolean b) {
                switch (i) {
                    case 0:
                        imageView.setImageResource(R.mipmap.home);
                        break;
                    case 1:
                        imageView.setImageResource(R.mipmap.found);
                        break;
                    case 2:
                        imageView.setImageResource(R.mipmap.subscribe);
                        break;
                }
            }

            @Override
            public void onEnter(int i, int i1, float v, boolean b) {
                switch (i) {
                    case 0:
                        imageView.setImageResource(R.mipmap.home_select);
                        break;
                    case 1:
                        imageView.setImageResource(R.mipmap.found_select);
                        break;
                    case 2:
                        imageView.setImageResource(R.mipmap.subscribe_select);
                        break;
                }
            }
        });
        return commonPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {

        return null;
    }
}
