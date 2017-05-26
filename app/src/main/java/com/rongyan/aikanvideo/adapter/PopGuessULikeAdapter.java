package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

import io.vov.vitamio.widget.VideoView;

/**
 * Created by XRY on 2017/5/21.
 */

public class PopGuessULikeAdapter extends CommonAdapter<Video> {
    private VideoView videoView;

    public PopGuessULikeAdapter(Context context, List<Video> list, VideoView videoView) {
        super(context, list);
        this.videoView = videoView;
    }

    public PopGuessULikeAdapter(Context context, List<Video> list, RecyclerView recyclerView, VideoView videoView) {
        super(context, list, recyclerView);
        this.videoView = videoView;
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_pop_guess_u_like;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, final Video item, int position) {
        viewHolder.setImageUrl(R.id.pop_guess_u_like_img, item.getImageURL())
                .setText(R.id.pop_guess_u_like_title, item.getTitle());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                    videoView.setVideoURI(Uri.parse(item.getVideoURL()));
                    videoView.start();
                }

            }
        });
    }
}
