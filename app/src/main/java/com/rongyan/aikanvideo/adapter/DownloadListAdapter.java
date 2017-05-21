package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.exampleenen.ruedy.imagelib.widget.IdentityImageView;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.bean.Download;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;

import java.util.List;

/**
 * Created by XRY on 2017/5/20.
 */

public class DownloadListAdapter extends CommonAdapter<Download> {

    public DownloadListAdapter(Context context, List<Download> list) {
        super(context, list);
    }

    public DownloadListAdapter(Context context, List<Download> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_download;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, Download item, int position) {
        final IdentityImageView progressImg = viewHolder.getView(R.id.download_progress_img);
        viewHolder.setText(R.id.download_list_title, item.getTitle())
                .setText(R.id.download_title_new, item.getTitlenews());

        progressImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressImg.getBigCircleImageView()
                        .setImageResource(R.drawable.ic_play_arrow_primary_24dp);
            }
        });
    }
}
