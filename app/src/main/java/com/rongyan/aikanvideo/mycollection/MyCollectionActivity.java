package com.rongyan.aikanvideo.mycollection;

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

public class MyCollectionActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle(getResources().getString(R.string.my_collection));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MyCollectionFragment fragment = (MyCollectionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_my_collection);

        if (fragment == null) {
            fragment = MyCollectionFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_my_collection);
        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new MyCollectionPresenter(this, fragment, lifeCycleSubject);
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
