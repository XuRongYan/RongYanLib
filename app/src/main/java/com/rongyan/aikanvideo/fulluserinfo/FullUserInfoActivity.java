package com.rongyan.aikanvideo.fulluserinfo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;

public class FullUserInfoActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_full_user_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle("完善个人信息");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FullUserInfoFragment fragment = (FullUserInfoFragment) getSupportFragmentManager().findFragmentById(R.id.frame_full_user_info);
        if (fragment == null) {
            fragment = FullUserInfoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_full_user_info);

        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new FullUserInfoPresenter(this, fragment, lifeCycleSubject);
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
