package com.rongyan.aikanvideo.guessYouLike;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.VideoAdapter;
import com.rongyan.aikanvideo.advertisement.AdvertisementActivity;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
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
    private VideoAdapter videoAdapter;

    public static GuessYouLikeFragment newInstance() {

        Bundle args = new Bundle();

        GuessYouLikeFragment fragment = new GuessYouLikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        videoAdapter = new VideoAdapter(getActivity(), list, guessYouLikeRecycler);
        guessYouLikeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        guessYouLikeRecycler.setAdapter(videoAdapter);
        videoAdapter.addHeaderView(R.layout.guess_you_like_header);

        mPresenter.getRank();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                Bundle extras = data.getExtras();
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getList(final List<Video> list) {
        videoAdapter.setOnBindHeaderOrFooter(new CommonAdapter.OnBindHeaderOrFooter() {
            @Override
            public void onHeader(ViewHolder viewHolder) {
                viewHolder.setImageUrl(R.id.guess_you_like_header, list.get(0).getImageURL())
                        .getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AdvertisementActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("video", list.get(0));
                        intent.putExtras(bundle);
                        getActivity().startActivityForResult(intent, 1);
                    }
                });
            }

            @Override
            public void onFooter(ViewHolder viewHolder) {

            }
        });
        List<Video> tmpList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            tmpList.add(list.get(i));
        }
        videoAdapter.addListAtEndAndNotify(tmpList);

    }
}
