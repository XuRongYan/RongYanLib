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
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.util.List;

/**
 * Created by XRY on 2017/5/16.
 */

public class SearchResultAdapter extends CommonAdapter<Video> {
    private static final String TAG = "SearchResultAdapter";

    public SearchResultAdapter(Context context, List<Video> list) {
        super(context, list);
    }

    public SearchResultAdapter(Context context, List<Video> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_search;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, final Video item, int position) {
        LogUtils.d(TAG, "onBindVH", "执行了");
        viewHolder.setText(R.id.item_search_title, item.getTitle())
                .setText(R.id.item_search_type, item.getType())
                .setText(R.id.item_search_whole, "")
                .setText(R.id.item_search_cast, item.getCatagory())
                .setImageUrl(R.id.item_search_img, item.getImageUrlAdress(), R.drawable.ic_failure);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", item);
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
