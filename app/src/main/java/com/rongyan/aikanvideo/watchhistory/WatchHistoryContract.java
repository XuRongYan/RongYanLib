package com.rongyan.aikanvideo.watchhistory;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.History;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 * Created by XRY on 2017/5/20.
 */

public interface WatchHistoryContract {
    interface Presenter extends BasePresenter {
        void getHistory();
    }

    interface View extends BaseView<Presenter> {
        void getList(List<History> list);
        void refreshList(List<Video> list);
        void setText(String text);
    }
}
