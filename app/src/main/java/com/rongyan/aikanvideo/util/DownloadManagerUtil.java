package com.rongyan.aikanvideo.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.rongyan.rongyanlibrary.util.LogUtils;

/**
 * Created by XRY on 2017/5/16.
 */

public class DownloadManagerUtil {
    private static final String TAG = "DownloadManagerUtil";
    private Context context;
    private DownloadManager manager;
    private DownloadManager.Request request;
    IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "下载任务已完成", Toast.LENGTH_SHORT).show();
            }
        }
    };


    public DownloadManagerUtil(Context context, Uri uri) {
        this.context = context;
        initDownloadManager();
        context.registerReceiver(receiver, filter);
        setUri(uri);

    }


    private void initDownloadManager() {
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void setUri(Uri uri) {
        request = new DownloadManager.Request(uri);
    }

    public void setAllowedNetWorkTypes(int flag) {
        if (request != null) {
            request.setAllowedNetworkTypes(flag);
        }
    }

    public void setTitle(String title) {
        request.setTitle(title);
    }

    public void setDescription(String description) {
        request.setDescription(description);
    }

    /**
     * 设置Notification可见的状态
     *
     * @param visibility Request.VISIBILITY_VISIBLE：在下载进行的过程中，通知栏中会一直显示该下载的Notification，当下载完成时，该Notification会被移除，这是默认的参数值。
     *                   Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED：在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该
     *                   Notification或者消除该Notification。
     *                   Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION：只有在下载完成后该Notification才会被显示。
     *                   Request.VISIBILITY_HIDDEN：不显示该下载请求的Notification。如果要使用这个参数，需要在应用的清单文件中加上DOWNLOAD_WITHOUT_NOTIFICATION权
     */
    public void setNotificationVisibility(int visibility) {
        request.setNotificationVisibility(visibility);
    }

    /**
     * 卸载后随文件消失
     *
     * @param uri
     */
    public void setDestinationUri(Uri uri) {
        request.setDestinationUri(uri);
    }

    public void setDestinationInExternalFilesDir(Context context, String ditType, String subPath) {
        request.setDestinationInExternalFilesDir(context, ditType, subPath);
    }

    public void setDestinationInExternalPublicDir(String dirType, String subPath) {
        request.setDestinationInExternalPublicDir(dirType, subPath);
    }

    public void cancelDownload(long... longs) {
        manager.remove(longs);
    }

    public long startDownload() {
        return manager.enqueue(request);
    }

    public void queryDownTask(DownloadManager downloadManager, int status) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(status);
        Cursor cursor = downloadManager.query(query);

        while (cursor.moveToNext()) {
            String downId = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String statuss = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String size = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

            LogUtils.i(TAG, "queryDownTask", "Id:" + downId + '\n' +
                    "Title:" + title + '\n' +
                    "Address:" + address + '\n' +
                    "Status:" + statuss + '\n' +
                    "Size:" + size + '\n' +
                    "SizeTotal" + sizeTotal);
        }
    }


}
