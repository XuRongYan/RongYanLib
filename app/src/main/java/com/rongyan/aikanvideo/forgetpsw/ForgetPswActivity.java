package com.rongyan.aikanvideo.forgetpsw;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.util.ActivityUtils;

import butterknife.Bind;

public class ForgetPswActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_psw;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        toolbar.setTitle("忘记密码");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ForgetPswFragment fragment = (ForgetPswFragment) getSupportFragmentManager().findFragmentById(R.id.frame_forget_psw);
        if (fragment == null) {
            fragment = ForgetPswFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_forget_psw);

        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), fragment);
        }

        new ForgetPswPresenter(this, fragment, lifeCycleSubject);

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
