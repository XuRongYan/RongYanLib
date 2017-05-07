package com.rongyan.aikanvideo.hotPlay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.RankAdapter;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.commonAdapter_recyclerView.MyDecoration;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Rank;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotPlayFragment extends BaseFragment implements HotPlayContract.View {

    @Bind(R.id.hot_play_recycler)
    RecyclerView recyclerView;

    private List<Rank> list = new ArrayList<>();
    private HotPlayContract.Presenter mPresenter;

    public static HotPlayFragment newInstance() {

        Bundle args = new Bundle();

        HotPlayFragment fragment = new HotPlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        RankAdapter adapter = new RankAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_hot_play;
    }

    @Override
    public void setPresenter(HotPlayContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
