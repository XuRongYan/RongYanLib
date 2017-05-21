package com.rongyan.aikanvideo.main;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.ImageAdapter;
import com.rongyan.aikanvideo.adapter.VideoAdapter;
import com.rongyan.aikanvideo.classification.ClassificationActivity;
import com.rongyan.aikanvideo.handler.ImageHandler;
import com.rongyan.aikanvideo.video.VideoActivity;
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
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements MainContract.View {
    private static final String TAG = "MainFragment";
    @Bind(R.id.main_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.main_movie)
    LinearLayout mainMovie;
    @Bind(R.id.main_teleplay)
    LinearLayout mainTeleplay;
    @Bind(R.id.main_variety)
    LinearLayout mainVariety;
    @Bind(R.id.main_news)
    LinearLayout mainNews;
    @Bind(R.id.main_classification)
    LinearLayout mainClassification;
    @Bind(R.id.main_refresh)
    SwipeRefreshLayout refreshLayout;
    private MainContract.Presenter mPresenter;
    public ImageHandler handler = new ImageHandler(new WeakReference<>(this));
    @Bind(R.id.play_viewpager)
    public ViewPager viewPager;
    @Bind(R.id.play_indicator)
    public MagicIndicator indicator;

    ArrayList<ImageView> list;
    List<Video> videoList = new ArrayList<>();
    private VideoAdapter videoAdapter;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initViews();
        initRecyclerView();
        return view;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_main;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessage(ImageHandler.MSG_BREAK_SILENT);
        if (mPresenter != null) {
            mPresenter.subscribe();

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        //离开时暂停，否则会报空指针
        handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initViews() {
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
        initRefresh();


    }

    private void initRefresh() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh(refreshLayout);
            }
        });
    }

    @Override
    public void initRecyclerView() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(manager);
        videoAdapter = new VideoAdapter(getActivity(), videoList, recyclerView);
        videoAdapter.addHeaderView(R.layout.recycler_header);
        videoAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, ViewHolder holder, int position) {
                goActivity(VideoActivity.class);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(videoAdapter);


    }

    @Override
    public void getList(List<Video> list) {
        videoAdapter.replaceList(list);
    }

    private void initMagicIndicator() {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(list.size());
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        indicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    @OnClick({R.id.main_classification,
            R.id.main_movie,
            R.id.main_news,
            R.id.main_teleplay,
            R.id.main_variety})
    public void click(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.main_classification:
                bundle.putString("title", getResources().getString(R.string.string_classification));
                goActivity(ClassificationActivity.class, bundle);
                break;
            case R.id.main_movie:
                bundle.putString("title", getResources().getString(R.string.string_movie));
                goActivity(ClassificationActivity.class, bundle);
                break;
            case R.id.main_news:
                bundle.putString("title", getResources().getString(R.string.string_news));
                goActivity(ClassificationActivity.class, bundle);
                break;
            case R.id.main_teleplay:
                bundle.putString("title", getResources().getString(R.string.string_teleplay));
                goActivity(ClassificationActivity.class, bundle);
                break;
            case R.id.main_variety:
                bundle.putString("title", getResources().getString(R.string.string_variety));
                goActivity(ClassificationActivity.class, bundle);
                break;
            default:
                break;
        }
    }

}
