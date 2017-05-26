package com.rongyan.aikanvideo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.advertisement.AdvertisementActivity;
import com.rongyan.rongyanlibrary.ImageLoader.ImageLoader;
import com.rongyan.rongyanlibrary.ImageLoader.ImageLoaderUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.List;

/**
 * Created by XRY on 2017/5/21.
 */

public class ImageUrlAdapter extends PagerAdapter {
    private static final String TAG = "ImageUrlAdapter";
    List<ImageView> list;
    private Context context;
    private List<Video> videoList;

    public ImageUrlAdapter(List<ImageView> list, List<Video> videoList, Context context) {
        this.list = list;
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        return 300;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // container.removeView(list.get(position));
    }

    public int getStartPageIndex() {
        int index = getCount() / 2;
        int remainder = index % list.size();
        index = index - remainder;
        return index;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //重新计算位置
        position %= list.size();
        if (position < 0) {
            position = list.size() + position;
        }
        final Video video = videoList.get(position);
        ImageView imageView = list.get(position);
        Log.d(TAG, position + "");
        ImageLoader loader = new ImageLoader.Builder()
                .url(video.getImageURL())
                .placeHolder(R.drawable.ic_failure)
                .imgView(imageView)
                .build();
        ImageLoaderUtil.getInstance().loadImage(context, loader);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdvertisementActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", video);
                intent.putExtras(bundle);
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });
        ViewParent parent = imageView.getParent();
        //如果View在之前已经添加到一个父组件，必须先remove，否则抛出IllegalStateException
        if (parent != null) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    public void addList(List<ImageView> imgList, List<Video> videoList) {
        imgList.clear();
        videoList.clear();
        list.addAll(imgList);
        this.videoList.addAll(videoList);
        notifyDataSetChanged();
    }

    public int getItemIndexForPosition(int position) {
        return position % list.size();
    }
}
