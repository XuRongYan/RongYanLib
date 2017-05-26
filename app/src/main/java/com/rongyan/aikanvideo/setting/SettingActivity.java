package com.rongyan.aikanvideo.setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;

public class SettingActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle(getResources().getString(R.string.setting));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SettingFragment fragment = (SettingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_setting);
        if (fragment == null) {
            fragment = SettingFragment.newInstance();
            fragment.setLifeCycleEventPublishSubject(lifeCycleSubject);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_setting);
        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new SettingPresenter(this, fragment, lifeCycleSubject);
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
