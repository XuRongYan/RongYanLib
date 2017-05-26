package com.rongyan.aikanvideo.mycollection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.MyCollectionAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.MyDecoration;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectionFragment extends BaseFragment implements MyCollectionContract.View{
    @Bind(R.id.my_collection_recycler)
    RecyclerView recyclerView;
    private List<Video> list = new ArrayList<>();

    private MyCollectionContract.Presenter mPresenter;
    private MyCollectionAdapter adapter;
    private TextView footView;

    public static MyCollectionFragment newInstance() {

        Bundle args = new Bundle();

        MyCollectionFragment fragment = new MyCollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        initRecyclerView();
        mPresenter.getCollection();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_my_collection;
    }

    @Override
    public void setPresenter(MyCollectionContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void getList(List<Video> list) {
        adapter.addListAtEndAndNotify(list);
    }

    @Override
    public int getUserId() {
        assert ((User) PreferencesUtil.getSerializable(getContext(), "user")) != null;
        return ((User) PreferencesUtil.getSerializable(getContext(), "user")) == null ? -1 : ((User) PreferencesUtil.getSerializable(getContext(), "user")).getUserid();
    }

    @Override
    public void setText(String string) {
        if (footView != null) {
            footView.setText(string);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL));
        adapter = new MyCollectionAdapter(getActivity(), list, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.addFooterView(R.layout.recycler_footer);
        adapter.setOnBindHeaderOrFooter(new CommonAdapter.OnBindHeaderOrFooter() {
            @Override
            public void onHeader(ViewHolder viewHolder) {

            }

            @Override
            public void onFooter(ViewHolder viewHolder) {
                footView = viewHolder.getView(R.id.foot_text);
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
