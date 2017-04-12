package com.rongyan.rongyanlibrary.myAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.List;

/**
 * Created by XRY on 2016/10/8.
 */

public abstract class MyAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mList;
    protected final int mItemLayoutId;

    public MyAdapter(Context mContext, List<T> mList, int mItemLayoutId) {
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mList = mList;
        this.mItemLayoutId = mItemLayoutId;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = getViewHolder(i, view, viewGroup);
        convert(viewHolder, getItem(i));
        return viewHolder.getConvertView();
    }

    /**
     * 通过ViewHolder把View找到，通过Item设置值
     *
     * @param holder
     * @param item
     */
    public abstract void convert(ViewHolder holder, T item);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    /**
     * 为listView添加一组数据
     *
     * @param collectEntities
     */
    public void addAll(Collection<? extends T> collectEntities) {
        mList.addAll(collectEntities);
        notifyDataSetChanged();
    }

    /**
     * 为listView添加一条数据
     *
     * @param e
     */
    public void add(T e) {
        mList.add(e);
        notifyDataSetChanged();
    }
}
