package com.rongyan.rongyanlibrary.util;

/**
 * Created by XRY on 2017/1/30.
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(String[] deniedPermissions);
}
