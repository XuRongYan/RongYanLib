package com.rongyan.aikanvideo.guessYouLike;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuessYouLikeActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_guess_you_like;
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(R.string.string_guess_you_like);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        GuessYouLikeFragment fragment = (GuessYouLikeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_guess_you_like);

        if (fragment == null) {
            fragment = GuessYouLikeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager()
                    , fragment
                    , R.id.frame_guess_you_like);
        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new GuessYouLikePresenter(fragment, this, lifeCycleSubject);
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
