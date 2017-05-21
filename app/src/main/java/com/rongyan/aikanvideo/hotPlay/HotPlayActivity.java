package com.rongyan.aikanvideo.hotPlay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotPlayActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_hot_play;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.string_three_month_hot_play);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        HotPlayFragment fragment = (HotPlayFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_hot_play);
        if (fragment == null) {
            fragment = HotPlayFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
                    , fragment
                    , R.id.frame_hot_play);
        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new HotPlayPresenter(fragment, this, lifeCycleSubject);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
