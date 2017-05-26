package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Plots;

import java.util.List;

import io.vov.vitamio.widget.VideoView;

/**
 * Created by XRY on 2017/5/22.
 */

public class PlotsAdapter extends CommonAdapter<Plots> {
    private VideoView videoView;

    public PlotsAdapter(Context context, List<Plots> list, VideoView videoView) {
        super(context, list);
        this.videoView = videoView;
    }

    public PlotsAdapter(Context context, List<Plots> list, VideoView videoView, RecyclerView recyclerView) {
        super(context, list, recyclerView);
        this.videoView = videoView;
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_pop_plots;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, final Plots item, int position) {
        viewHolder.setText(R.id.item_pop_plots_text, item.getTitle())
                .getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.seekTo(item.getPosition());
            }
        });
    }
}
