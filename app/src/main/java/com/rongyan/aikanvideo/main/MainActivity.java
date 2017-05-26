package com.rongyan.aikanvideo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.MainViewPagerAdapter;
import com.rongyan.aikanvideo.adapter.MyIndicatorAdapter;
import com.rongyan.aikanvideo.download.DownloadActivity;
import com.rongyan.aikanvideo.find.FindFragment;
import com.rongyan.aikanvideo.login.LoginActivity;
import com.rongyan.aikanvideo.mycollection.MyCollectionActivity;
import com.rongyan.aikanvideo.order.OrderFragment;
import com.rongyan.aikanvideo.search.SearchActivity;
import com.rongyan.aikanvideo.setting.SettingActivity;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.aikanvideo.watchhistory.WatchHistoryActivity;
import com.rongyan.rongyanlibrary.ImageLoader.ImageLoader;
import com.rongyan.rongyanlibrary.ImageLoader.ImageLoaderUtil;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.base.BaseAppManager;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.util.LogUtils;
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


    private static final String TAG = "LifeStyleActivityA";

    private long exitTime = 0;
    ArrayList<BaseFragment> fragments = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();
    private BaseFragment mainFragment;
    private BaseFragment findFragment;
    private BaseFragment orderFragment;
    private SearchView searchView;
    private MainPresenter mainPresenter;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        android.util.Log.d(TAG, "onCreate");
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

        User user = PreferencesUtil.getSerializable(this, "user");
        if (user == null) {
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
            ImageView headImg = (ImageView) header.findViewById(R.id.header_head_img);
            TextView headName = (TextView) header.findViewById(R.id.header_name);
            TextView headPhone = (TextView) header.findViewById(R.id.header_phone);
            headName.setText(user.getNickname());
            headPhone.setText(user.getCellphone());
            ImageLoader loader = new ImageLoader.Builder()
                    .imgView(headImg)
                    .url(user.getHeadimg())
                    .placeHolder(R.mipmap.ic_launcher)
                    .build();
            ImageLoaderUtil.getInstance().loadImage(this, loader);
            mainNav.addHeaderView(header);
        }

        mainNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_history:
                        if (PreferencesUtil.getSerializable(MainActivity.this, "user") != null) {
                            goActivity(WatchHistoryActivity.class);
                        } else {
                            ToastUtils.showShort(MainActivity.this, getResources().getString(R.string.login_first));
                        }
                        break;
                    case R.id.my_collect:
                        if (PreferencesUtil.getSerializable(MainActivity.this, "user") != null) {
                            goActivity(MyCollectionActivity.class);
                        } else {
                            ToastUtils.showShort(MainActivity.this, getResources().getString(R.string.login_first));
                        }
                        break;
                    case R.id.my_setting:
                        goActivity(SettingActivity.class);
                        break;
                    case R.id.my_download:
                        if (PreferencesUtil.getSerializable(MainActivity.this, "user") != null) {
                            goActivity(DownloadActivity.class);
                        } else {
                            ToastUtils.showShort(MainActivity.this, getResources().getString(R.string.login_first));
                        }
                        break;
                }
                return true;
            }
        });

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
    protected void onStart() {
        super.onStart();
        android.util.Log.d(TAG, "onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume", "onResume");
//        if (mainPresenter == null) {
//            initPresenter();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        android.util.Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        android.util.Log.d(TAG, "onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.d(TAG, "ondestroy");
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
                goActivity(SearchActivity.class);
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
        mainPresenter = new MainPresenter((MainContract.View) mainFragment, this, lifeCycleSubject);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                Bundle extras = data.getExtras();
                Intent intent = new Intent(this, VideoActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                break;
        }
    }
}
