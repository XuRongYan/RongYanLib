package com.rongyan.aikanvideo.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by XRY on 2017/4/24.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> list = new ArrayList<>();
    private RecyclerView mRecyclerView;

    public static final int TYPE_NORMAL = 1000;
    public static final int TYPE_HEADER = 1001;
    public static final int TYPE_FOOTER = 1002;

    private View headerView;
    private View footerView;

    public VideoAdapter(List<Video> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new ViewHolder(headerView);
        } else if (viewType == TYPE_FOOTER) {
            return new ViewHolder(footerView);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_item, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (hasHeaderView()) {
                position--;
            }
            ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.video_img);
            imageView.setImageResource(R.mipmap.ic_launcher);
            TextView title = (TextView) holder.itemView.findViewById(R.id.video_title);
            title.setText("title");
            TextView news = (TextView) holder.itemView.findViewById(R.id.video_news);
            news.setText("news");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        int count = list == null ? 0 : list.size();
        if (footerView != null) {
            count++;
        }
        if (headerView != null) {
            count++;
        }
        return count;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mRecyclerView == null && mRecyclerView != recyclerView) {
            mRecyclerView = recyclerView;
        }
        ifGridLayoutManager();
    }

    public void addHeaderView(View view) {
        if (hasHeaderView()) {
            throw new IllegalStateException("header view is already exists");
        } else {
            //避免产生自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            headerView = view;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }
    }

    public void addFooterView(View view) {
        if (hasFooterView()) {
            throw new IllegalStateException("footer view is already exists");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            footerView = view;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) {
            return;
        }
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        //如果是GridLayout就获取他的spanSize
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager)
                    .getSpanSizeLookup();

            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeaderView(position) || isFooterView(position) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }

    }

    private boolean hasHeaderView() {
        return headerView != null;
    }

    private boolean hasFooterView() {
        return footerView != null;
    }

    private boolean isHeaderView(int position) {
        return hasHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return hasFooterView() && position == getItemCount() - 1;
    }



    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
