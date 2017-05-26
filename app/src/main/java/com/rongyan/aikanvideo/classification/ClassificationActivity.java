package com.rongyan.aikanvideo.classification;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.search.SearchActivity;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClassificationActivity extends BaseActivity {
    public static String title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_classification;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
        }
        if (title != null) {
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ClassTabFragment fragment = (ClassTabFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_classification);

        if (fragment == null) {
            fragment = ClassTabFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.frame_classification);

        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new ClassificationPresenter(fragment, this, lifeCycleSubject);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.search:
                goActivity(SearchActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
