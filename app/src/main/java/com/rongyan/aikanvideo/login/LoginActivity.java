package com.rongyan.aikanvideo.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.base.BaseAppManager;
import com.rongyan.rongyanlibrary.util.ActivityUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by XRY on 2017/4/22.
 */

public class LoginActivity extends BaseActivity {
    private long exitTime = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame_login);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.content_frame_login);

        } else {
            ActivityUtils.showFragment(getSupportFragmentManager(), loginFragment);
        }

        /**
         * 注意这里传的Context必须是Activity的Context否则Progress的显示会出错
         */
        new LoginPresenter(loginFragment, this, lifeCycleSubject);
    }

    /**
     * 按两次BACK键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showShort(this, getString(R.string.text_one_more_click));
                exitTime = System.currentTimeMillis();
            } else {
                BaseAppManager.getInstance().clearAll();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
