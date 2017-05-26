package com.rongyan.aikanvideo.main;

import android.support.v4.widget.SwipeRefreshLayout;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 *
 * Created by XRY on 2017/4/23.
 */

public interface MainContract {
    interface Presenter extends BasePresenter {
        void getFirstPage();
        void refresh(int userid, final SwipeRefreshLayout swipeRefreshLayout);
        void getMainLike(int userid);
    }

    interface View extends BaseView<Presenter> {
        void initViews();
        void initRecyclerView();
        void getList(List<Video> list);
        void getMainLike(List<Video> list);
        void load();
        void endLoad();
    }
}
