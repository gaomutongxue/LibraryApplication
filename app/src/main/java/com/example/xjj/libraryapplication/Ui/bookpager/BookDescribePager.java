package com.example.xjj.libraryapplication.Ui.bookpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookDescribePager extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_book_describe)
    TextView tvBookDescribe;
    @BindView(R.id.tv_book_about)
    TextView tvBookAbout;
    @BindView(R.id.tv_book_loctation)
    TextView tvBookLoctation;
    @BindView(R.id.zuozhe_name)
    TextView zuozheName;
    @BindView(R.id.kucun)
    TextView kucun;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_book_describe, null);
        unbinder = ButterKnife.bind(this, content);
        EventBus.getDefault().register(this);
        initView(content);
        registerListener(content);

        return content;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Book book) {
        tvBookDescribe.setText(book.info);
        zuozheName.setText(zuozheName.getText() + book.autor);
        tvBookLoctation.setText(book.price);
        kucun.setText(kucun.getText()+book.getId());

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
