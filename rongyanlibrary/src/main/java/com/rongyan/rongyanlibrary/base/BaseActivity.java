package com.rongyan.rongyanlibrary.base;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rongyan.rongyanlibrary.R;
import com.rongyan.rongyanlibrary.util.AppUtils;
import com.rongyan.rongyanlibrary.util.PermissionListener;

import java.util.ArrayList;
import java.util.List;



/**
 * 所有activity实现这个基类
 * 实现{@link BaseView}中的方法
 * Created by XRY on 2016/8/31.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static String TAG_LOG = null;

    protected Context mContext = null;

    private static BaseAppManager mBaseAppManager = BaseAppManager.getInstance();

    private static PermissionListener mListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        TAG_LOG = this.getClass().getSimpleName();
        //将activity添加到自定义堆栈
        mBaseAppManager.addActivity(this);
        //绑定视图
        if (getContentView() != 0) {
            LayoutInflater inflater = getLayoutInflater();
            View inflate = inflater.inflate(getContentView(), null);
            setContentView(inflate);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(this.getResources().getColor(R.color.colorDarkGray));
        }

        AppUtils.hideKeyboard(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseAppManager.removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
    }

    protected abstract int getContentView();

    protected abstract void initViews();

    /**
     * ----------------implements methods in BaseView--------------------------------------
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void goActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void goActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void goActivityForResult(Class<?> cls, int resultCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, resultCode,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void goActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * 运行时权限处理
     */
    public static void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mBaseAppManager.getTopActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(mBaseAppManager.getTopActivity(),
                    permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            //实现权限接口
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (permissions.length > 0) {
                    List<String> deniedPermission = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermission.add(permission);
                        }
                    }
                    if (deniedPermission.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(
                                deniedPermission.toArray(new String[deniedPermission.size()]));
                    }
                }
                break;
            default:
                break;
        }
    }
}
