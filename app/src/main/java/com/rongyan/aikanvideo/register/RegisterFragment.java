package com.rongyan.aikanvideo.register;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.util.AppUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    public static final int CLICKABLE = 1;
    public static final int UNCLICKABLE = 0;
    private int time = 90;
    @Bind(R.id.resister_phone)
    TextInputLayout resisterPhone;
    @Bind(R.id.register_input_psw)
    TextInputLayout registerInputPsw;
    @Bind(R.id.resister_psw_confirm)
    TextInputLayout registerPswConfirm;
    @Bind(R.id.register_verification_code)
    TextInputLayout registerVerificationCode;
    @Bind(R.id.register_get_verification_code)
    Button registerGetVerificationCode;
    @Bind(R.id.btn_register)
    Button btnRegister;

    private String phone;
    private String psw;
    private String pswConfirm;
    private String verificationCode;
    private RegisterContract.Presenter mPresenter;

    public MyHandler handler;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_register;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isUserNameValid() {
        phone = resisterPhone.getEditText().getText().toString();
        return AppUtils.isPhoneNumberValid(phone);
    }

    @Override
    public boolean isPasswordValid() {
        return true;
    }

    @Override
    public boolean setError(String error) {
        ToastUtils.showShort(getContext(), error);
        return true;
    }

    @Override
    public boolean setUsernameError(String error) {
        resisterPhone.setError(error);
        return true;
    }

    @Override
    public boolean setPswError(String error) {
        registerInputPsw.setError(error);
        return false;
    }

    @Override
    public boolean setPswConfirmError(String error) {
        registerPswConfirm.setError(error);
        return true;
    }

    @Override
    public boolean setVerificationCodeError(String error) {
        registerGetVerificationCode.setError(error);
        return true;
    }

    @Override
    public String getUsername() {
        phone = resisterPhone.getEditText().getText().toString();
        return phone;
    }

    @Override
    public String getPsw() {
        psw = registerInputPsw.getEditText().getText().toString();
        return psw;
    }

    @Override
    public String getPswConfirm() {
        pswConfirm = registerPswConfirm.getEditText().getText().toString();
        return pswConfirm;
    }

    @Override
    public String getVerificationCode() {
        verificationCode = registerVerificationCode.getEditText().getText().toString();
        return verificationCode;
    }

    @Override
    public void onRegisterSuc() {
        getActivity().finish();
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        handler = new MyHandler(this, registerGetVerificationCode);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_register, R.id.register_get_verification_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                mPresenter.register();
                break;

            case R.id.register_get_verification_code:
                mPresenter.getVerificationCode();

                break;
        }
    }

    static class MyHandler extends Handler {
        WeakReference<Button> reference;
        WeakReference<RegisterFragment> fragmentWeakReference;

        public MyHandler(RegisterFragment fragment, Button button) {
            this.reference = new WeakReference<>(button);
            this.fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            Button registerGetVerificationCode = reference.get();
            RegisterFragment registerFragment = fragmentWeakReference.get();
            if (registerFragment != null && registerGetVerificationCode != null){
                switch (msg.what) {
                    case CLICKABLE:
                        registerGetVerificationCode.setClickable(true);
                        registerGetVerificationCode.setText(registerFragment.getContext().getString(R.string.string_get_verification_code));
                        registerFragment.time = 90;
                        break;
                    case UNCLICKABLE:
                        if (registerGetVerificationCode.isClickable()) {
                            registerGetVerificationCode.setClickable(false);
                        }
                        registerGetVerificationCode.setText("(" + registerFragment.time-- + ")" + "秒后重新获取");
                        break;
                }
            }
        }
    }
}
