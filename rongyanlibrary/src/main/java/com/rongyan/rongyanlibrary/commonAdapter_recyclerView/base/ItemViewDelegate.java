package com.rongyan.rongyanlibrary.commonAdapter_recyclerView.base;

/**
 * Created by XRY on 2017/4/22.
 */

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
