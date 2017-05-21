package com.rongyan.aikanvideo.search;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 * Created by XRY on 2017/5/16.
 */

public interface SearchContract {
    interface Presenter extends BasePresenter {
        void submitQuery(String text);
    }

    interface View extends BaseView<Presenter> {
        void getList(List<Video> list);
        void refreshList(List<Video> list);
        void setText(String text);
        void closeSearchView();
    }
}
