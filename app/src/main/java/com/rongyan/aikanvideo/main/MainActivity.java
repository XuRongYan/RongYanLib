package com.rongyan.aikanvideo.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.MainViewPagerAdapter;
import com.rongyan.aikanvideo.find.FindFragment;
import com.rongyan.aikanvideo.order.OrderFragment;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.base.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;
    @Bind(R.id.main_tab)
    TabLayout mainTab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_nav)
    NavigationView mainNav;
    @Bind(R.id.main_drawer)
    DrawerLayout mainDrawer;

    ArrayList<BaseFragment> fragments = new ArrayList<>();


    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainDrawer, toolbar, 0, 0);
        mainDrawer.addDrawerListener(toggle);
        toggle.syncState();
        BaseFragment mainFragment = MainFragment.newInstance();
        BaseFragment findFragment = FindFragment.newInstance();
        BaseFragment orderFragment = OrderFragment.newInstance();

        fragments.add(mainFragment);
        fragments.add(findFragment);
        fragments.add(orderFragment);

        mainViewpager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), fragments));
        mainTab.setupWithViewPager(mainViewpager);

        one = mainTab.getTabAt(0);
        two = mainTab.getTabAt(1);
        three = mainTab.getTabAt(2);

        assert one != null;
        assert two != null;
        assert three != null;

        one.setIcon(R.mipmap.home);
        two.setIcon(R.mipmap.found);
        three.setIcon(R.mipmap.subscribe);

        initEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initEvents() {
        mainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mainTab.getTabAt(0)) {
                    one.setIcon(R.mipmap.home_select);
                    mainViewpager.setCurrentItem(0);
                } else if (tab == mainTab.getTabAt(1)) {
                    two.setIcon(R.mipmap.found_select);
                    mainViewpager.setCurrentItem(1);
                } else if (tab == mainTab.getTabAt(2)) {
                    three.setIcon(R.mipmap.subscribe_select);
                    mainViewpager.setCurrentItem(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mainTab.getTabAt(0)) {
                    one.setIcon(R.mipmap.home);
                    mainViewpager.setCurrentItem(0);
                } else if (tab == mainTab.getTabAt(1)) {
                    two.setIcon(R.mipmap.found);
                    mainViewpager.setCurrentItem(1);
                } else if (tab == mainTab.getTabAt(2)) {
                    three.setIcon(R.mipmap.subscribe);
                    mainViewpager.setCurrentItem(2);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
