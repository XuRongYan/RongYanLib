package com.rongyan.rongyanlibrary.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongyan.rongyanlibrary.R;

import butterknife.ButterKnife;


/**
 * BaseFragment for all the fragments defined in the project
 * 1.extend from this abstract class to do some base operation
 * 2.do operation in initViews()
 * Created by XRY on 2016/8/31.
 */
public abstract class BaseFragment extends Fragment {

    protected static String TAG_LOG = null;

    protected Context mContext;

    private View contentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set tag
        TAG_LOG = this.getClass().getSimpleName();

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            int resId = getContentViewID();
            contentView = inflater.inflate(resId, container, false);
            ButterKnife.bind(this, contentView);
            initViews(contentView);
            return contentView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViews(View rootView);

    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewID();

    /**
     * 等候加载
     *
     * @param cancelable 按返回键是否取消
     */
    protected Dialog dialog;
    protected void holdView(boolean cancelable) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        dialog = builder.create();
        dialog.setTitle(null);
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setCancelable(cancelable);
        dialog.show();
        dialog.setContentView(view);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(20000);
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }).start();
    }





    public void goActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    public void goActivity(Class<?> cls, Bundle... bundles) {
        Intent intent = new Intent(getActivity(), cls);
        for (Bundle bundle : bundles) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void goActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }


}
