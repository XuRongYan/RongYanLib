package com.rongyan.aikanvideo.handler;

import android.os.Handler;
import android.os.Message;

import com.rongyan.aikanvideo.main.MainFragment;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.lang.ref.WeakReference;

/**
 * Created by XRY on 2017/4/24.
 */

public class ImageHandler extends Handler {

    /**
     * 请求更新显示View
     */
    public static final int MSG_UPDATE_IMAGE = 1;
    /**
     * 请求暂停轮播
     */
    public static final int MSG_KEEP_SILENT = 2;
    /**
     * 请求恢复轮播
     */
    public static final int MSG_BREAK_SILENT = 3;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
     * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    public static final int MSG_PAGE_CHANGED = 4;
    /**
     * 轮播间隔时间
     */
    public static final long MSG_DELAY = 3000;

    private WeakReference<MainFragment> weakReference;
    private int currentItem = 0;

    public ImageHandler(WeakReference<MainFragment> weakReference) {
        this.weakReference = weakReference;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        LogUtils.e("ImageHandler", "msg", msg.what + "");
        MainFragment mainFragment = weakReference.get();
        if (mainFragment == null) {
            return;
        }
        //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
        if (mainFragment.handler.hasMessages(MSG_UPDATE_IMAGE)) {
            mainFragment.handler.removeMessages(MSG_UPDATE_IMAGE);
        }

        switch (msg.what) {
            case MSG_UPDATE_IMAGE:
                //翻页
                currentItem ++;
                LogUtils.d("ImageHandler", "update", "vpCurrentItem:" + mainFragment.viewPager.getCurrentItem() + " currentItem:" + currentItem);
                mainFragment.viewPager.setCurrentItem(currentItem);

                //准备下次播放
                mainFragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_KEEP_SILENT:
                //暂停
                break;
            case MSG_BREAK_SILENT:
                mainFragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            case MSG_PAGE_CHANGED:
                //记录当前页号，避免播放时候页面显示不正确
                currentItem = msg.arg1;
                //准备下次播放
                mainFragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                break;
            default:
                break;
        }
    }
}
