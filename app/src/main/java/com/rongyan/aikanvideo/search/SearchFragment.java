package com.rongyan.aikanvideo.search;


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
import com.rongyan.aikanvideo.adapter.SearchResultAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.MyDecoration;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.mauker.materialsearchview.MaterialSearchView;
import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements SearchContract.View{
    @Bind(R.id.search_recycler)
    RecyclerView recyclerView;
    public String title;
    private String qurey;
    public boolean isLoad = false;
    private SearchContract.Presenter mPresenter;
    private SearchResultAdapter adapter;
    private List<Video> list = new ArrayList<>();
    private TextView footView;
    private List<Video> tmpList;
    private List<List<Video>> teleplayList = new ArrayList<>();
    private MaterialSearchView materialSearchView;
    public static SearchFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        LogUtils.d(TAG_LOG, "newInstance", title);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        isLoad = true;
        title = getArguments().getString("title");
        LogUtils.d(TAG_LOG, "initViews", getArguments().getString("title"));
        initRecyclerView();
        if (tmpList != null) {
            adapter.replaceList(tmpList);
            tmpList = null;
        }

    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_search;
    }

    private void initView() {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getList(List<Video> list) {
        if (adapter != null) {
            adapter.addListAtEndAndNotify(list);
        } else {
            tmpList = list;
        }
    }

    @Override
    public void refreshList(List<Video> list) {
        adapter.replaceList(list);
    }

    @Override
    public void setText(String text) {
        if (footView != null) {
            footView.setText(text);
        }
    }

    @Override
    public void closeSearchView() {
        if (materialSearchView != null && materialSearchView.isOpen()) {
            materialSearchView.closeSearch();
        }

    }

    @Override
    public void getTeleList(List<List<Video>> lists) {
        adapter.setTeleplayList(lists);
        List<Video> teleplayList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            teleplayList.add(lists.get(i).get(0));
        }
        adapter.addListAtEndAndNotify(teleplayList);
    }

    public void setQurey(String qurey) {
        this.qurey = qurey;

    }

    private void initRecyclerView() {
        MyDecoration decoration;
        decoration = new MyDecoration(getActivity(), MyDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(decoration);
        adapter = new SearchResultAdapter(getActivity(), list, teleplayList, recyclerView);
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
                        if (title.equals("电视剧")) {
                            mPresenter.getTelePlay(qurey);
                        } else {
                            mPresenter.submitQuery(qurey);
                        }

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
//        if (qurey != null) {
//            mPresenter.submitQuery(qurey);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
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

    public void setMaterialSearchView(MaterialSearchView materialSearchView) {
        this.materialSearchView = materialSearchView;
    }
}
