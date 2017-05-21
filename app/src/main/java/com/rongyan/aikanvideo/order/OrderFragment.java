package com.rongyan.aikanvideo.order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.OrderAdapter;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderFragment extends BaseFragment implements OrderContract.View {
    @Bind(R.id.order_recycler)
    RecyclerView recyclerView;

    private OrderContract.Presenter mPresenter;

    public static OrderFragment newInstance() {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        List<Order> list = new ArrayList<>();
        OrderAdapter adapter = new OrderAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_order;
    }

    @Override
    public void setPresenter(OrderContract.Presenter presenter) {
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
