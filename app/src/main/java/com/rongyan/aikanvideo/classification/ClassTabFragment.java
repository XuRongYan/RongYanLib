package com.rongyan.aikanvideo.classification;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.ImageAdapter;
import com.rongyan.aikanvideo.adapter.VideoAdapter;
import com.rongyan.aikanvideo.handler.ClassificationImageHandler;
import com.rongyan.aikanvideo.handler.ImageHandler;
import com.rongyan.aikanvideo.widget.ScaleCircleNavigator;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassTabFragment extends BaseFragment {

    @Bind(R.id.classification_play_viewpager)
    public ViewPager viewPager;
    @Bind(R.id.classification_play_indicator)
    MagicIndicator indicator;
    @Bind(R.id.classification_recycler)
    RecyclerView recyclerView;

    ArrayList<ImageView> list;
    public ClassificationImageHandler handler = new ClassificationImageHandler(new WeakReference<>(this));
    public static ClassTabFragment newInstance() {

        Bundle args = new Bundle();

        ClassTabFragment fragment = new ClassTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_class_tab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initPlayIndicator();
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initPlayIndicator() {
        ImageView imageView1 = new ImageView(getActivity());
        ImageView imageView2 = new ImageView(getActivity());
        ImageView imageView3 = new ImageView(getActivity());
        imageView1.setImageResource(R.mipmap.ic_launcher);
        imageView2.setImageResource(R.mipmap.found);
        imageView3.setImageResource(R.mipmap.subscribe);
        list = new ArrayList<>();
        list.add(imageView1);
        list.add(imageView2);
        list.add(imageView3);
        viewPager.setAdapter(new ImageAdapter(list, getActivity()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);

        initMagicIndicator();
    }

    private void initMagicIndicator() {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(list.size());
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        indicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    private void initRecyclerView() {
        List<Video> list = new ArrayList<>();
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        VideoAdapter videoAdapter = new VideoAdapter(list);
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.recycler_header, null));


    }
}
