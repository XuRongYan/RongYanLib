package com.rongyan.aikanvideo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
 * Created by XRY on 2017/5/7.
 */

public class RankAdapter extends CommonAdapter<Video> {

    public RankAdapter(Context context, List<Video> list) {
        super(context, list);
    }

    public RankAdapter(Context context, List<Video> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.rank_item;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, final Video item, int position) {

        if (position < 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewHolder.setBackgroundColor(R.id.rank_item_text_rank, context.getResources().getColor(R.color.colorPrimary, context.getTheme()));
            } else {
                viewHolder.setBackgroundColor(R.id.rank_item_text_rank, context.getResources().getColor(R.color.colorPrimary));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewHolder.setBackgroundColor(R.id.rank_item_text_rank, context.getResources().getColor(R.color.colorButtonGray, context.getTheme()));
            } else {
                viewHolder.setBackgroundColor(R.id.rank_item_text_rank, context.getResources().getColor(R.color.colorButtonGray));
            }
        }
        viewHolder.setText(R.id.rank_item_text_rank, (position + 1) + "")
                .setText(R.id.rank_item_text_title, item.getTitle())
                .getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdvertisementActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", item);
                intent.putExtras(bundle);
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });
    }
}
