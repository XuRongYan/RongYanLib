package com.rongyan.aikanvideo.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.main.MainActivity;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.util.AppUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by XRY on 2017/4/22.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @Bind(R.id.login_username_wrapper)
    TextInputLayout loginUsernameWrapper;
    @Bind(R.id.login_psw_wrapper)
    TextInputLayout loginPswWrapper;
    @Bind(R.id.login_forget_psw)
    TextView loginForgetPsw;
    @Bind(R.id.login_register)
    TextView loginRegister;
    @Bind(R.id.btn_login)
    Button btnLogin;
    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View rootView) {
        User user = Hawk.get("user");
        if (user != null){
            loginUsernameWrapper.getEditText().setText(user.getCellphone());
        }
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_login;
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isUserNameValid() {
        String username = loginUsernameWrapper.getEditText().getText().toString();
        return AppUtils.isPhoneNumberValid(username);
    }

    @Override
    public boolean isPasswordValid() {
        return true;
    }

    @Override
    public boolean setError(String error) {
        ToastUtils.showShort(getContext(),error);
        return true;
    }

    @Override
    public boolean setUsernameError(String error) {
        loginUsernameWrapper.setError(error);
        return true;
    }

    @Override
    public boolean setPswError(String error) {
        loginPswWrapper.setError(error);
        return true;
    }

    @Override
    public void onLoginSuc() {
        ToastUtils.showShort(getContext(), "success");
    }

    @Override
    public String getUsername() {
        return loginUsernameWrapper.getEditText().getText().toString();
    }

    @Override
    public String getPsw() {
        return loginPswWrapper.getEditText().getText().toString();
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
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @OnClick({R.id.btn_login})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //mPresenter.login();
                goActivity(MainActivity.class);
                break;
        }
    }
}
