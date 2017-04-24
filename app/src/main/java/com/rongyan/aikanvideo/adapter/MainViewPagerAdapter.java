package com.rongyan.aikanvideo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rongyan.rongyanlibrary.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by XRY on 2017/4/23.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter{
    ArrayList<BaseFragment> fragments;

    private String[] mTitles = new String[]{"首页", "发现", "订阅"};

    public MainViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
