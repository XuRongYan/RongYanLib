package com.rongyan.aikanvideo.classification;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.ImageAdapter;
import com.rongyan.aikanvideo.adapter.VideoAdapter;
import com.rongyan.aikanvideo.handler.ClassificationImageHandler;
import com.rongyan.aikanvideo.handler.ImageHandler;
import com.rongyan.aikanvideo.widget.ScaleCircleNavigator;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
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
public class ClassTabFragment extends BaseFragment implements ClassificationContract.View{

    @Bind(R.id.classification_play_viewpager)
    public ViewPager viewPager;
    @Bind(R.id.classification_play_indicator)
    MagicIndicator indicator;
    @Bind(R.id.classification_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.classification_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.classification_play_nest_scroll)
    NestedScrollView scrollView;
    ArrayList<ImageView> list;
    private static final String TAG = "ClassTabFragment";
    public ClassificationImageHandler handler = new ClassificationImageHandler(new WeakReference<>(this));
    private ClassificationContract.Presenter mPresenter;
    private VideoAdapter videoAdapter;
    private String title;
    private TextView footText;


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
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        title = getActivity().getIntent().getExtras().getString("title");
        initRefresh();
        initPlayIndicator();
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mPresenter.refresh(title, swipeRefreshLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
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
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        videoAdapter = new VideoAdapter(getActivity(), list, recyclerView);
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.addHeaderView(R.layout.recycler_header);
        videoAdapter.addFooterView(R.layout.recycler_footer);
        videoAdapter.setOnBindHeaderOrFooter(new CommonAdapter.OnBindHeaderOrFooter() {
            @Override
            public void onHeader(ViewHolder viewHolder) {

            }

            @Override
            public void onFooter(ViewHolder viewHolder) {
                footText = viewHolder.getView(R.id.foot_text);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    Log.i(TAG, "Scroll UP");
                }

                if (scrollY == 0) {
                    Log.i(TAG, "TOP SCROLL");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.i(TAG, "BOTTOM SCROLL");
                    mPresenter.getList(title);
                }
            }
        });
    }

    private void initRefresh() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh(title, swipeRefreshLayout);
            }
        });
    }

    @Override
    public void setPresenter(ClassificationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getList(List<Video> list) {
        videoAdapter.addListAtEndAndNotify(list);
    }

    @Override
    public void refreshList(List<Video> list) {
        videoAdapter.replaceList(list);
    }

    @Override
    public void setText(String text) {
        if (footText != null) {
            footText.setText(text);
        }
    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


}
