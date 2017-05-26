package com.rongyan.aikanvideo.watchhistory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;

public class WatchHistoryActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected int getContentView() {
        return R.layout.activity_watch_history;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.watch_history);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        WatchHistoryFragment fragment = (WatchHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_watch_history);

        if (fragment == null) {
            fragment = WatchHistoryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_watch_history);
        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new WatchHistoryPresenter(this, fragment, lifeCycleSubject);

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
