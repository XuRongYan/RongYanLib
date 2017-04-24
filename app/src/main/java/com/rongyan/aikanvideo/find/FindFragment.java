package com.rongyan.aikanvideo.find;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {

    public static FindFragment newInstance() {

        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected int getContentViewID() {
        return 0;
    }

}
