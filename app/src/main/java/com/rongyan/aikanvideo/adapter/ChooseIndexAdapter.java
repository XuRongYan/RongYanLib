package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

import io.vov.vitamio.widget.VideoView;

/**
 * Created by XRY on 2017/5/24.
 */

public class ChooseIndexAdapter extends CommonAdapter<Video> {
    private VideoView videoView;
    private int selectPosition = 0;
    private VideoActivity activity;
    //private ArrayList<Video> teleplayList = new ArrayList<>();

    public ChooseIndexAdapter(Context context, List<Video> list, VideoView videoView, VideoActivity activity) {
        super(context, list);
        this.videoView = videoView;
        this.activity = activity;
    }

    public ChooseIndexAdapter(Context context, List<Video> list, RecyclerView recyclerView, VideoView videoView, VideoActivity activity) {
        super(context, list, recyclerView);
        this.videoView = videoView;
        this.activity = activity;

    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_pop_choose_index;
    }

    @Override
    public void onBindVH(final ViewHolder viewHolder, final Video item, final int position) {
        if (position == selectPosition) {
            viewHolder.setBackgroundRes(R.id.item_pop_choose_index, R.drawable.bg_choose_index_selected);
        } else {
            viewHolder.setBackgroundColor(R.id.item_pop_choose_index, context.getResources().getColor(R.color.colorTransparentGray));
        }
        viewHolder.setText(R.id.item_pop_choose_index, (position + 1) + "")
                .getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = position;
                //videoView.stopPlayback();
                videoView.setVideoURI(Uri.parse(item.getVideoURL()));
                videoView.start();
                activity.danmakuView.release();
                activity.getVideoComment();
                notifyDataSetChanged();
            }
        });
    }
}
