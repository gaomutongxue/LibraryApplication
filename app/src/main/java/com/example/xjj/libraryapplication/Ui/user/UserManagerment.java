package com.example.xjj.libraryapplication.Ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserManagerment extends BaseFragment {
    @BindView(R.id.bt_user_login)
    Button btUserLogin;
    @BindView(R.id.rl_no_Login)
    FrameLayout rlNoLogin;

    Unbinder unbinder;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_manager, null);
        unbinder = ButterKnife.bind(this, content);
        initView(content);
        registerListener(content);

        return content;

    }

    private void registerListener(View content) {

    }

    private void initView(View content) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
