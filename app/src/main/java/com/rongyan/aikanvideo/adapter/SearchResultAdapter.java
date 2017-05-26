package com.rongyan.aikanvideo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.advertisement.AdvertisementActivity;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XRY on 2017/5/16.
 */

public class SearchResultAdapter extends CommonAdapter<Video> {
    private static final String TAG = "SearchResultAdapter";
    private List<List<Video>> teleplayList;

    public SearchResultAdapter(Context context, List<Video> list, List<List<Video>> teleplayList) {
        super(context, list);
        this.teleplayList = teleplayList;
    }

    public SearchResultAdapter(Context context, List<Video> list, List<List<Video>> teleplayList, RecyclerView recyclerView) {
        super(context, list, recyclerView);
        this.teleplayList = teleplayList;
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_search;
    }

    @Override
    public void onBindVH(ViewHolder viewHolder, final Video item, final int position) {
        LogUtils.d(TAG, "onBindVH", "执行了");
        Video video = new Video(item.getVideoid(), item.getTitle(), item.getTitlenew(), item.getTimelength(), item.getVideoURL(), item.getImageURL());
        try {
            if (teleplayList != null && teleplayList.size() != 0) {
                if (item.getTitle().contains("第1集")) {
                    String title = video.getTitle().substring(0, video.getTitle().lastIndexOf("第"));
                    viewHolder.setText(R.id.item_search_title, title);
                }
            } else {
                viewHolder.setText(R.id.item_search_title, item.getTitle());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        viewHolder.setText(R.id.item_search_type, item.getType())
                .setText(R.id.item_search_whole, "")
                .setText(R.id.item_search_cast, item.getCatagory())
                .setImageUrl(R.id.item_search_img, item.getImageURL(), R.drawable.ic_failure);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                try {
                    if (teleplayList != null && teleplayList.size() != 0) {
                    ArrayList<Video> tmpParcelList = new ArrayList<>();
                    Parcel parcel = Parcel.obtain();
                    for (int i = 0; i < teleplayList.get(position).size(); i++) {
                        Video video = Video.CREATOR.createFromParcel(parcel);
                        video.setVideoid(teleplayList.get(position).get(i).getVideoid());
                        video.setFilename(teleplayList.get(position).get(i).getFilename());
                        video.setVideodirectory(teleplayList.get(position).get(i).getVideodirectory());
                        video.setTitle(teleplayList.get(position).get(i).getTitle());
                        video.setTitlenew(teleplayList.get(position).get(i).getTitlenew());
                        video.setDescription(teleplayList.get(position).get(i).getDescription());
                        video.setType(teleplayList.get(position).get(i).getType());
                        video.setCatagory(teleplayList.get(position).get(i).getCatagory());
                        video.setLabels(teleplayList.get(position).get(i).getLabels());
                        video.setPlaytimes(teleplayList.get(position).get(i).getPlaytimes());
                        video.setTimelength(teleplayList.get(position).get(i).getTimelength());
                        video.setVideoDate(teleplayList.get(position).get(i).getVideoDate());
                        video.setIsFirstPage(teleplayList.get(position).get(i).getIsFirstPage());
                        video.setVideoURL(teleplayList.get(position).get(i).getVideoURL());
                        video.setImageURL(teleplayList.get(position).get(i).getImageURL());
                        video.setNumberOfVideoplay(teleplayList.get(position).get(i).getNumberOfVideoplay());
                        video.setBelongtovideoid(teleplayList.get(position).get(i).getBelongtovideoid());
                        video.setTVplayindex(teleplayList.get(position).get(i).getTVplayindex());
                        tmpParcelList.add(video);
                    }
                    parcel.recycle();
                    bundle.putParcelableArrayList("teleplay", tmpParcelList);
                }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


                bundle.putSerializable("video", item);
                Intent intent = new Intent(context, AdvertisementActivity.class);
                intent.putExtras(bundle);
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });
    }

    public void setTeleplayList(List<List<Video>> teleplayList) {
        this.teleplayList = teleplayList;
    }
}
