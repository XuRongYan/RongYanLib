package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.advertisement.AdvertisementActivity;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 * Created by XRY on 2017/5/14.
 */

public class VideoAdapter extends CommonAdapter<Video> {

    public VideoAdapter(Context context, List<Video> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.video_item;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, final Video item, int position) {
        viewHolder.setImageUrl(R.id.video_img, item.getImageUrlAdress(), R.drawable.ic_failure)
                .setText(R.id.video_title, item.getTitle())
                .setText(R.id.video_news, item.getTitlenew());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", item);
                Intent intent = new Intent(context, AdvertisementActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }
}
