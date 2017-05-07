package com.rongyan.aikanvideo.classification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends BaseFragment {

    public static FilterFragment newInstance() {

        Bundle args = new Bundle();

        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_filter;
    }

}
