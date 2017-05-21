package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.History;

import java.util.List;

/**
 * Created by XRY on 2017/5/20.
 */

public class HistoryAdapter extends CommonAdapter<History> {
    public HistoryAdapter(Context context, List<History> list) {
        super(context, list);
    }

    public HistoryAdapter(Context context, List<History> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_search;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, History item, int position) {

    }
}
