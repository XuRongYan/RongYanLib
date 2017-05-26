package com.rongyan.aikanvideo.classification;

import android.support.v4.widget.SwipeRefreshLayout;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 * Created by XRY on 2017/4/30.
 */

public interface ClassificationContract {
    interface Presenter extends BasePresenter {
        void getList(String key);
        void refresh(String key, SwipeRefreshLayout layout);
        void getFirstPage();

    }

    interface View extends BaseView<Presenter> {
        void getList(List<Video> list);
        void refreshList(List<Video> list);
        void setText(String text);
        void getFirstPage(List<Video> list);
    }
}
