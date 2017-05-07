package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.ImageLoader.ImageLoader;
import com.rongyan.rongyanlibrary.ImageLoader.ImageLoaderUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Order;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XRY on 2017/4/29.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<Order> list = new ArrayList<>();
    private Context context;

    public OrderAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.order_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = list.get(position);
        final ImageView subscribe = (ImageView) holder.itemView.findViewById(R.id.order_item_subscribe);
        TextView title = (TextView) holder.itemView.findViewById(R.id.order_item_title);
        TextView content = (TextView) holder.itemView.findViewById(R.id.order_item_content);
        CircleImageView img = (CircleImageView) holder.itemView.findViewById(R.id.order_item_img);
        ImageLoader imageLoader = new ImageLoader.Builder()
                .imgView(img)
                .url(order.getImg())
                .placeHolder(R.mipmap.ic_launcher)
                .build();
        ImageLoaderUtil.getInstance().loadImage(context, imageLoader);
        content.setText(order.getContent());
        title.setText(order.getTitle());
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order.isSubscribe()) {
                    order.setSubscribe(false);
                    subscribe.setImageResource(R.drawable.subscribe);
                } else {
                    order.setSubscribe(true);
                    subscribe.setImageResource(R.drawable.subscribed);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
