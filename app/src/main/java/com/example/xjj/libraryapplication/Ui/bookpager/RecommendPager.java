package com.example.xjj.libraryapplication.Ui.bookpager;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xjj.libraryapplication.Adapter.RankAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;
import com.example.xjj.libraryapplication.event.MessageEvent;
import com.example.xjj.libraryapplication.listener.StartBookDetailListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecommendPager extends BaseFragment {
    @BindView(R.id.rv_list_recommend)
    RecyclerView rvListRecommend;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Book> books;
    private boolean isRvStateVertical=true;
    private RankAdapter rankAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recommend_pager, null);
        initView(content);
        registerListener(content);
        resisterEventBus();
        unbinder = ButterKnife.bind(this, content);
        return content;

    }

    private void registerListener(View content) {
     rankAdapter.setOnItemClickListener(new StartBookDetailListener(books,getContext()));
    }

    private void initView(View content) {
        books=new ArrayList<Book>();
        initRv();

    }

    private void initRv() {
        books.add(new Book("排行板"));
        books.add(new Book("aaa"));
        books.add(new Book("aaa"));
        rankAdapter = new RankAdapter(R.layout.list_vertical_book_item, books);
        linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager=new GridLayoutManager(getContext(),3);
        rvListRecommend = (RecyclerView) findViewById(R.id.rv_list_recommend);
        rvListRecommend.setLayoutManager(linearLayoutManager);
        rvListRecommend.setAdapter(rankAdapter);
    }

    @Subscribe
    public void changeRvState(MessageEvent messageEvent) {
        Log.d("改变rv状态按钮","被碰到了");
        if (isRvStateVertical){
            rvListRecommend.setLayoutManager(gridLayoutManager);
            isRvStateVertical=false;
        }else {
            rvListRecommend.setLayoutManager(linearLayoutManager);
            isRvStateVertical=true;
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
