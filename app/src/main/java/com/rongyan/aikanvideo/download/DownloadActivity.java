package com.rongyan.aikanvideo.download;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;

public class DownloadActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_download;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.download_center);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DownloadFragment fragment = (DownloadFragment) getSupportFragmentManager().findFragmentById(R.id.frame_download);
        if (fragment == null) {
            fragment = DownloadFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_download);
        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new DownloadPresenter(this, fragment, lifeCycleSubject);

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
