package com.rongyan.aikanvideo.watchhistory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.HistoryAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.MyDecoration;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.History;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.rongyan.aikanvideo.classification.ClassificationActivity.title;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchHistoryFragment extends BaseFragment implements WatchHistoryContract.View{
    @Bind(R.id.watch_history_recycler)
    RecyclerView recyclerView;
    private WatchHistoryContract.Presenter mPresenter;
    private List<History> list = new ArrayList<>();
    private TextView footView;
    private HistoryAdapter adapter;


    public static WatchHistoryFragment newInstance() {

        Bundle args = new Bundle();

        WatchHistoryFragment fragment = new WatchHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        MyDecoration decoration;
        decoration = new MyDecoration(getActivity(), MyDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(decoration);
        adapter = new HistoryAdapter(getActivity(), list, recyclerView);
        adapter.addFooterView(R.layout.recycler_footer);
        LogUtils.d(TAG_LOG, "initRecyclerView", title);
        adapter.setOnBindHeaderOrFooter(new CommonAdapter.OnBindHeaderOrFooter() {
            @Override
            public void onHeader(ViewHolder viewHolder) {

            }

            @Override
            public void onFooter(ViewHolder viewHolder) {
                footView = viewHolder.getView(R.id.foot_text);
                LogUtils.d(TAG_LOG, "onFooter", "执行了");
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    Log.i("recyclerView", ("lastVisiblePosition" + lastPosition + "ItemCount" + (recyclerView.getLayoutManager().getItemCount() - 1)));
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

        });
        recyclerView.setAdapter(adapter);

        mPresenter.getHistory();
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

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_watch_history;
    }

    @Override
    public void setPresenter(WatchHistoryContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void getList(List<History> list) {
        if (adapter != null) {
            adapter.addListAtEndAndNotify(list);
        }
    }

    @Override
    public void refreshList(List<Video> list) {

    }

    @Override
    public void setText(String text) {
        if (footView != null) {
            footView.setText(text);
        }
    }
}
