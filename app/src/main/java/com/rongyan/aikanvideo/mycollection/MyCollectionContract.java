package com.rongyan.aikanvideo.mycollection;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 * Created by XRY on 2017/5/20.
 */

public interface MyCollectionContract {
    interface Presenter extends BasePresenter {
        void getCollection();
    }

    interface View extends BaseView<Presenter> {
        void getList(List<Video> list);
        int getUserId();
        void setText(String string);
    }
}
