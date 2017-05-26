package com.rongyan.aikanvideo.hotPlay;

import com.rongyan.rongyanlibrary.base.BasePresenter;
import com.rongyan.rongyanlibrary.base.BaseView;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 * Created by XRY on 2017/5/7.
 */

public interface HotPlayContract {
    interface Presenter extends BasePresenter {
        void getRank();
    }

    interface View extends BaseView<Presenter> {
        void getList(List<Video> list);
    }
}
