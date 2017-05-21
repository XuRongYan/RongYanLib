package com.rongyan.aikanvideo.register;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle(R.string.string_register);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RegisterFragment fragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_register);

        if (fragment == null) {
            fragment = RegisterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_register);
        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new RegisterPresenter(fragment, this, lifeCycleSubject);
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
