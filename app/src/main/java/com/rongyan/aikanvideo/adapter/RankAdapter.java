package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Rank;

import java.util.List;

/**
 * Created by XRY on 2017/5/7.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<Rank> list;
    private Context context;

    public RankAdapter(Context context, List<Rank> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rank rank = list.get(position);
        TextView tvRank = (TextView) holder.itemView.findViewById(R.id.rank_item_text_rank);
        TextView tvTitle = (TextView) holder.itemView.findViewById(R.id.rank_item_text_title);
        if (rank.getRank() <= 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRank.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary, context.getTheme()));
            } else {
                tvRank.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvRank.setBackgroundColor(context.getResources().getColor(R.color.colorButtonGray, context.getTheme()));
            } else {
                tvRank.setBackgroundColor(context.getResources().getColor(R.color.colorButtonGray));
            }
        }
        tvRank.setText(rank.getRank() + "");
        tvTitle.setText(rank.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
