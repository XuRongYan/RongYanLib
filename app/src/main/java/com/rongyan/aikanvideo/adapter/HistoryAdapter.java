package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.History;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

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
    public void onBindVH(ViewHolder viewHolder, final History item, int position) {
        viewHolder.setText(R.id.item_search_title, item.getTitle())
                .setText(R.id.item_search_type, item.getTitlenew())
                .setText(R.id.item_search_cast, item.getWatchtime().replace('T', ' '))
                .setImageUrl(R.id.item_search_img, item.getImageURL());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Video video = new Video(item.getVideoid(), item.getTitle(), item.getTitlenew(), item.getTimelast(), item.getImageURL(), item.getVideoURL());
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", video);
                Intent intent = new Intent(context, VideoActivity.class);
                context.startActivity(intent, bundle);
            }
        });
    }
}
