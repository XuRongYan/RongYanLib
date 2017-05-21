package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.util.List;

/**
 * Created by XRY on 2017/5/21.
 */

public class PopGuessULikeAdapter extends CommonAdapter<Video> {
    public PopGuessULikeAdapter(Context context, List<Video> list) {
        super(context, list);
    }

    public PopGuessULikeAdapter(Context context, List<Video> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_pop_guess_u_like;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, Video item, int position) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(context, "click");
            }
        });
    }
}
