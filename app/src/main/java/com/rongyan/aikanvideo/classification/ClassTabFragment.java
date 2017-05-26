package com.rongyan.aikanvideo.classification;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.rongyan.aikanvideo.adapter.ImageUrlAdapter;
import com.rongyan.aikanvideo.adapter.VideoAdapter;
import com.rongyan.aikanvideo.handler.ClassificationImageHandler;
import com.rongyan.aikanvideo.handler.ImageHandler;
import com.rongyan.aikanvideo.widget.ScaleCircleNavigator;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.util.LogUtils;

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
    private List<Video> videoList = new ArrayList<>();
    private ImageUrlAdapter imageUrlAdapter;
    private ScaleCircleNavigator scaleCircleNavigator;


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getFirstPage();

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

    private void initPlayIndicator() {final List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LogUtils.d("ImageAdapter", "fori", i + " ");
            ImageView image = new ImageView(getActivity());
            image.setImageResource(R.mipmap.ic_launcher);
            videoList.add(new Video());
            imgList.add(image);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                //indicator.onPageSelected(position);
                if (position <= 2 || position >= imageUrlAdapter.getCount() - 3) {
                    int page = imageUrlAdapter.getItemIndexForPosition(position);
                    int newPosition = imageUrlAdapter.getStartPageIndex() + page;
                    viewPager.setCurrentItem(newPosition - 1);
                    viewPager.setCurrentItem(newPosition, true);
                }
                handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //indicator.onPageScrollStateChanged(state);
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

        imageUrlAdapter = new ImageUrlAdapter(imgList, videoList, getActivity());
        viewPager.setAdapter(imageUrlAdapter);
    }

    private void initMagicIndicator(int size) {
        scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(size);
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
                switch (title) {
                    case "电视剧":
                        viewHolder.setText(R.id.textView, "强档新剧");
                        break;
                    case "电影":
                        viewHolder.setText(R.id.textView, "最新上映");
                        break;
                    case "综艺":
                        viewHolder.setText(R.id.textView, "热播综艺");
                        break;
                    case "新闻":
                        viewHolder.setText(R.id.textView, "热点新闻");
                        break;
                }
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

    @Override
    public void getFirstPage(List<Video> list) {
        initMagicIndicator(list.size());
        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgList.add(imageView);
        }

        notifyRefreshAdapter(imgList, list);

        viewPager.setCurrentItem(imageUrlAdapter.getStartPageIndex());

        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
    }



    private void notifyRefreshAdapter(List<ImageView> imgList, List<Video> videoList) {
        if (imageUrlAdapter != null) {
            imageUrlAdapter = null;
        }
        imageUrlAdapter = new ImageUrlAdapter(imgList, videoList, getActivity());
        viewPager.setAdapter(imageUrlAdapter);
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
