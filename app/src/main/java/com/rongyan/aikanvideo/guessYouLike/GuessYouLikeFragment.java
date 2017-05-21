package com.rongyan.aikanvideo.guessYouLike;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.VideoAdapter;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuessYouLikeFragment extends BaseFragment implements GuessYouLikeContract.View {
    @Bind(R.id.guess_you_like_recycler)
    RecyclerView guessYouLikeRecycler;
    private GuessYouLikeContract.Presenter mPresenter;
    private List<Video> list = new ArrayList<>();

    public static GuessYouLikeFragment newInstance() {

        Bundle args = new Bundle();

        GuessYouLikeFragment fragment = new GuessYouLikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        VideoAdapter videoAdapter = new VideoAdapter(getActivity(), list, guessYouLikeRecycler);
        guessYouLikeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        guessYouLikeRecycler.setAdapter(videoAdapter);
        videoAdapter.addHeaderView(R.layout.guess_you_like_header);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_guess_you_like;
    }

    @Override
    public void setPresenter(GuessYouLikeContract.Presenter presenter) {
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
