package com.rongyan.aikanvideo.forgetpsw;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.util.AppUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.OnClick;

import static com.rongyan.aikanvideo.register.RegisterFragment.CLICKABLE;
import static com.rongyan.aikanvideo.register.RegisterFragment.UNCLICKABLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPswFragment extends BaseFragment implements ForgetPswContract.View{
    @Bind(R.id.btn_forget_psw)
    Button btnGetVerificationCode;
    @Bind(R.id.forget_psw_phone)
    TextInputLayout cellphone;
    @Bind(R.id.forget_psw_input_psw)
    TextInputLayout newPsw;
    @Bind(R.id.forget_psw_psw_confirm)
    TextInputLayout newPswConfirm;
    @Bind(R.id.forget_psw_verification_code)
    TextInputLayout verificationCode;
    @Bind(R.id.forget_psw_get_verification_code)
    Button btnForgetPsw;
    private ForgetPswContract.Presenter mPresenter;

    private int time = 90;
    public MyHandler handler;
    public static ForgetPswFragment newInstance() {

        Bundle args = new Bundle();

        ForgetPswFragment fragment = new ForgetPswFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initViews(View rootView) {
        handler = new MyHandler(this, btnForgetPsw);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_forget_psw;
    }

    @Override
    public void setPresenter(ForgetPswContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onSubmit() {
        getActivity().finish();
    }

    @Override
    public boolean isUserNameValid() {
        return AppUtils.isPhoneNumberValid(cellphone.getEditText().getText().toString());
    }

    @Override
    public boolean isPasswordValid() {
        return false;
    }

    @Override
    public boolean setError(String error) {
        ToastUtils.showShort(getContext(), error);
        return true;
    }

    @Override
    public boolean setUsernameError(String error) {
        cellphone.setError(error);
        return true;
    }

    @Override
    public boolean setPswError(String error) {
        newPsw.setError(error);
        return true;
    }

    @Override
    public boolean setPswConfirmError(String error) {
        newPswConfirm.setError(error);
        return true;
    }

    @Override
    public boolean setVerificationCodeError(String error) {
        verificationCode.setError(error);
        return true;
    }

    @OnClick({R.id.btn_forget_psw, R.id.forget_psw_get_verification_code})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_psw:
                if (!newPsw.equals(newPswConfirm)) {
                    mPresenter.submit(cellphone.getEditText().getText().toString(), newPsw.getEditText().getText().toString(), verificationCode.getEditText().getText().toString());
                } else {
                    newPswConfirm.setError(getActivity().getString(R.string.string_different_psw));
                }
                break;
            case R.id.forget_psw_get_verification_code:
                mPresenter.getVerification(cellphone.getEditText().getText().toString());
                break;
        }
    }

    static class MyHandler extends Handler {
        WeakReference<Button> reference;
        WeakReference<ForgetPswFragment> fragmentWeakReference;

        public MyHandler(ForgetPswFragment fragment, Button button) {
            this.reference = new WeakReference<>(button);
            this.fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            Button forgetpswGetCode = reference.get();
            ForgetPswFragment forgetPswFragment = fragmentWeakReference.get();
            if (forgetPswFragment != null && forgetpswGetCode != null){
                switch (msg.what) {
                    case CLICKABLE:
                        forgetpswGetCode.setClickable(true);
                        forgetpswGetCode.setText(forgetPswFragment.getContext().getString(R.string.string_get_verification_code));
                        forgetPswFragment.time = 90;
                        break;
                    case UNCLICKABLE:
                        if (forgetpswGetCode.isClickable()) {
                            forgetpswGetCode.setClickable(false);
                        }
                        forgetpswGetCode.setText("(" + forgetPswFragment.time-- + ")" + "秒后重新获取");
                        break;
                }
            }
        }
    }
}
