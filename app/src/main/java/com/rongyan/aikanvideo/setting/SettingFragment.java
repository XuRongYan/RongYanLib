package com.rongyan.aikanvideo.setting;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.AddPreferenceAdapter;
import com.rongyan.aikanvideo.fulluserinfo.FullUserInfoActivity;
import com.rongyan.aikanvideo.login.LoginActivity;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.LogUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment implements SettingContract.View{
    private static final String TAG = "SettingFragment";
    @Bind(R.id.btn_setting_exit)
    Button btnExit;
    @Bind(R.id.ll_setting_add_preference)
    LinearLayout addPreference;
    @Bind(R.id.ll_setting_complete_info)
    LinearLayout fullUserInfo;

    private List<String> preference = new ArrayList<>();

    private SettingContract.Presenter mPresenter;
    private AlertDialog addPreferenceDialog;
    private User user;
    private PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject;
    private AddPreferenceAdapter adapter;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initViews(View rootView) {
        user = PreferencesUtil.getSerializable(getActivity(), "user");
        preference.add("新闻");
        preference.add("专题");
        preference.add("脱口秀");
        preference.add("娱乐");
        preference.add("电影");
        preference.add("综艺");
        preference.add("体育");
        preference.add("真人秀");
        preference.add("动漫");
        preference.add("财经");
        preference.add("科技");
        preference.add("电视剧");
        initDialog();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_setting;
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick({R.id.btn_setting_exit, R.id.ll_setting_add_preference,R.id.ll_setting_complete_info})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_setting_exit:
                exit();
                break;
            case R.id.ll_setting_add_preference:
                if (user != null) {
                    addPreferenceDialog.show();
                } else {
                    ToastUtils.showShort(getActivity(), getActivity().getString(R.string.login_first));
                }
                break;
            case R.id.ll_setting_complete_info:
                if (user != null) {
                    goActivity(FullUserInfoActivity.class);
                } else {
                    ToastUtils.showShort(getActivity(), getActivity().getString(R.string.login_first));
                }
                break;
        }
    }

    public PublishSubject<ActivityLifeCycleEvent> getLifeCycleEventPublishSubject() {
        return lifeCycleEventPublishSubject;
    }

    public void setLifeCycleEventPublishSubject(PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject) {
        this.lifeCycleEventPublishSubject = lifeCycleEventPublishSubject;
    }

    private void exit() {
        PreferencesUtil.reset(getContext());
        goActivity(LoginActivity.class);
    }

    /**
     * 初始化用户偏好对话框
     */
    private void initDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_preference, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.add_preference_recycler);
        final TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.add_preference_label);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AddPreferenceAdapter(getActivity(), preference, recyclerView);
        recyclerView.setAdapter(adapter);
        addPreferenceDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] strings = textInputLayout.getEditText().getText().toString().split(";");
                        List<String> list = adapter.getCheckedItems();
                        StringBuilder preference = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                preference.append(list.get(i));
                            } else {
                                preference.append("-" + list.get(i));
                            }
                        }
                        LogUtils.d(TAG, "preference", preference.toString());

                        StringBuilder label = new StringBuilder();
                        for (int i = 0; i < strings.length; i++) {
                            if (i == 0) {
                                label.append(strings[i]);
                            } else {
                                label.append("-" + strings[i]);
                            }
                        }
                        LogUtils.d(TAG, "label", label.toString());

                        sendPreference(preference.toString(), label.toString());

                    }
                }).create();

    }

    private void sendPreference(String preference, String labels) {
        ApiService apiService = new ApiService();
        Observable<HttpResult> observable = apiService.getService(NetworkApi.class).sendUserPreference(user.getUserid(), preference, labels);
        HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(getActivity()) {
            @Override
            protected void _onNext(Object o) {
                ToastUtils.showShort(getActivity(), getString(R.string.add_preference_success));

            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG, "sendPreference", message);
                ToastUtils.showShort(getActivity(), getString(R.string.add_fail));
            }

            @Override
            protected void _onCompleted() {
            }
        }, null,  ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject, false, false, false);
    }
}
