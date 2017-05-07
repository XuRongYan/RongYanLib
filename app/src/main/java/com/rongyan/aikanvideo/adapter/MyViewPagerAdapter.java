package com.rongyan.aikanvideo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rongyan.rongyanlibrary.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by XRY on 2017/4/30.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> list;

    public MyViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
