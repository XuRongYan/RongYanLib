package com.rongyan.aikanvideo.fulluserinfo;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.MyDecoration;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;
import com.rongyan.rongyanlibrary.base.BaseFragment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.util.AppUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class FullUserInfoFragment extends BaseFragment implements FullUserInfoContract.View {
    @Bind(R.id.full_info_female)
    RadioButton radioFemale;
    @Bind(R.id.full_info_male)
    RadioButton radioMale;
    @Bind(R.id.full_info_nickname)
    TextInputLayout nickname;
    @Bind(R.id.full_info_email)
    TextInputLayout email;
    @Bind(R.id.full_info_age)
    TextInputLayout age;
    @Bind(R.id.full_info_show_dialog)
    ImageView showDialog;
    @Bind(R.id.full_info_career)
    EditText career;
    @Bind(R.id.full_info_submit)
    Button submit;
    @Bind(R.id.full_info_radioGroup)
    RadioGroup radioGroup;

    private FullUserInfoContract.Presenter mPresenter;
    private User user;
    private AlertDialog chooseCareerDialog;

    public static FullUserInfoFragment newInstance() {

        Bundle args = new Bundle();

        FullUserInfoFragment fragment = new FullUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.full_info_submit, R.id.full_info_show_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.full_info_submit:
                mPresenter.submit(user.getUserid());
                break;
            case R.id.full_info_show_dialog:
                chooseCareerDialog.show();
                break;
        }
    }

    @Override
    protected void initViews(View rootView) {
        user = PreferencesUtil.getSerializable(getActivity(), "user");
        radioGroup.check(R.id.full_info_male);
        initDialog();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_full_user_inforagment;
    }

    @Override
    public void setPresenter(FullUserInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getNickname() {
        return nickname.getEditText().getText().toString();
    }

    @Override
    public String getEmail() {
        return email.getEditText().getText().toString();
    }

    @Override
    public String getAge() {
        return age.getEditText().getText().toString();
    }

    @Override
    public String getGender() {
        if (radioFemale.isChecked()) {
            return "女";
        }
        return "男";
    }

    @Override
    public String getCareer() {
        return career.getText().toString();
    }

    @Override
    public boolean isEmailValid() {
        return AppUtils.isEmailValid(email.getEditText().getText().toString());
    }

    @Override
    public boolean setError(String error) {
        ToastUtils.showShort(getContext(), error);
        return true;
    }

    @Override
    public boolean setNicknameError(String error) {
        nickname.setError(error);
        return true;
    }

    @Override
    public boolean setEmailError(String error) {
        email.setError(error);
        return true;
    }

    @Override
    public boolean setAgeError(String error) {
        age.setError(error);
        return true;
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    private void initDialog() {
        List<String> list = new ArrayList<>();
        list.add("孕妇");
        list.add("教师");
        list.add("学生");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choode_career, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialog_choose_career_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL));
        recyclerView.setAdapter(new CommonAdapter<String>(getActivity(), list) {
            @Override
            public int setLayoutId(int position) {
                return R.layout.item_choose_career;
            }

            @Override
            public void onBindVH(ViewHolder viewHolder, final String item, int position) {
                viewHolder.setText(R.id.item_choose_career_text, item)
                        .getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        career.setText(item);
                        chooseCareerDialog.dismiss();
                    }
                });
            }
        });

        chooseCareerDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }
}
