package com.rongyan.rongyanlibrary.commonAdapter_recyclerView;

import android.content.Context;
import android.view.LayoutInflater;

import com.rongyan.rongyanlibrary.commonAdapter_recyclerView.base.ItemViewDelegate;
import com.rongyan.rongyanlibrary.commonAdapter_recyclerView.base.ViewHolder;

import java.util.List;

/**
 * 通用适配器
 * Created by XRY on 2017/4/22.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mData;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context mContext, List<T> mData, Context mContext1, final int mLayoutId, List<T> mData1) {
        super(mContext, mData);
        this.mContext = mContext1;
        this.mLayoutId = mLayoutId;
        this.mData = mData1;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);
}
