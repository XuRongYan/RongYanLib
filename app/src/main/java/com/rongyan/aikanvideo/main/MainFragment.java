package com.rongyan.aikanvideo.main;


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
public class MainFragment extends BaseFragment {


    public static MainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    protected void initViews(View rootView) {
        
    }

    @Override
    protected int getContentViewID() {
        return 0;
    }

}
