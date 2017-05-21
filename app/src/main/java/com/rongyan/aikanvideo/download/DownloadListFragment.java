package com.rongyan.aikanvideo.download;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseFragment;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadListFragment extends BaseFragment {
    @Bind(R.id.download_recycler)
    RecyclerView recyclerView;

    private String title;

    public static DownloadListFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        DownloadListFragment fragment = new DownloadListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        title = getArguments().getString("title");
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_download_list;
    }

}
