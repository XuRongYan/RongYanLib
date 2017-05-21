package com.rongyan.aikanvideo.mycollection;


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
public class MyCollectionFragment extends BaseFragment implements MyCollectionContract.View{
    @Bind(R.id.my_collection_recycler)
    RecyclerView recyclerView;

    private MyCollectionContract.Presenter mPresenter;

    public static MyCollectionFragment newInstance() {

        Bundle args = new Bundle();

        MyCollectionFragment fragment = new MyCollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_my_collection;
    }

    @Override
    public void setPresenter(MyCollectionContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
