package com.rongyan.aikanvideo.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.MainViewPagerAdapter;
import com.rongyan.aikanvideo.adapter.MyIndicatorAdapter;
import com.rongyan.aikanvideo.bean.LoginUser;
import com.rongyan.aikanvideo.find.FindFragment;
import com.rongyan.aikanvideo.login.LoginActivity;
import com.rongyan.aikanvideo.order.OrderFragment;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.base.BaseAppManager;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {
    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.main_nav)
    NavigationView mainNav;
    @Bind(R.id.main_drawer)
    DrawerLayout mainDrawer;
    @Bind(R.id.main_indicator)
    MagicIndicator mainIndicator;

    private long exitTime = 0;
    ArrayList<BaseFragment> fragments = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();
    private BaseFragment mainFragment;
    private BaseFragment findFragment;
    private BaseFragment orderFragment;
    private SearchView searchView;


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
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        View headerNotLogin = LayoutInflater.from(this).inflate(R.layout.nav_header_not_login, null);
        headerNotLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(LoginActivity.class);
            }
        });

        if (LoginUser.user == null) {
            if (mainNav.getHeaderCount() != 0) {
                mainNav.removeView(headerNotLogin);
                mainNav.removeView(header);
            }
            mainNav.addHeaderView(headerNotLogin);
        } else {
            if (mainNav.getHeaderCount() != 0) {
                mainNav.removeView(header);
                mainNav.removeView(headerNotLogin);
            }
            mainNav.addHeaderView(header);
        }

        titleList.add("首页");
        titleList.add("发现");
        titleList.add("订阅");
        mainNav.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainDrawer, toolbar, 0, 0);
        mainDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mainFragment = MainFragment.newInstance();
        findFragment = FindFragment.newInstance();
        orderFragment = OrderFragment.newInstance();

        fragments.add(mainFragment);
        fragments.add(findFragment);
        fragments.add(orderFragment);

        mainViewpager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), fragments, titleList));
        ViewPagerHelper.bind(mainIndicator, mainViewpager);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new MyIndicatorAdapter(this, mainViewpager));
        commonNavigator.setAdjustMode(true);

        mainIndicator.setNavigator(commonNavigator);
        initPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initPresenter() {
        new MainPresenter((MainContract.View) mainFragment, this);
    }


    /**
     * 按两次BACK键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showShort(this, getString(R.string.text_one_more_click));
                exitTime = System.currentTimeMillis();
            } else {
                BaseAppManager.getInstance().clearAll();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }
}
