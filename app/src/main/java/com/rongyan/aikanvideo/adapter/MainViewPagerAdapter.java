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

    ArrayList<String> titleList;

    public MainViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments, ArrayList<String> titleList) {
        super(fm);
        this.fragments = fragments;
        this.titleList = titleList;
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
        return titleList.get(position);
    }
}
