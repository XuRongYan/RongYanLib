package com.rongyan.aikanvideo.download;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.MainViewPagerAdapter;
import com.rongyan.rongyanlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends BaseFragment implements DownloadContract.View{
    @Bind(R.id.download_tab)
    TabLayout tabLayout;
    @Bind(R.id.download_viewpager)
    ViewPager viewPager;

    private String[] titles = {"已缓存", "正在缓存"};
    private List<BaseFragment> list = new ArrayList<>();

    private DownloadContract.Presenter mPresenter;

    public static DownloadFragment newInstance() {

        Bundle args = new Bundle();

        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        List<String> titleList = new ArrayList<>();
        titleList.add(titles[0]);
        titleList.add(titles[1]);
        BaseFragment completeFgm = DownloadListFragment.newInstance(titles[0]);
        BaseFragment downloadingFgm = DownloadListFragment.newInstance(titles[1]);
        list.add(completeFgm);
        list.add(downloadingFgm);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getActivity().getSupportFragmentManager(), list, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_download;
    }

    @Override
    public void setPresenter(DownloadContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
