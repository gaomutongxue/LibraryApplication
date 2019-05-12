package com.example.xjj.libraryapplication.Ui.bookpager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xjj.libraryapplication.Adapter.BookCommentAdapter;
import com.example.xjj.libraryapplication.Adapter.RankAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.Enity.Comment;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookCommentPager extends BaseFragment {
    @BindView(R.id.rv_list_book_comment)
    RecyclerView rvListBookComment;
    Unbinder unbinder;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private LinearLayoutManager linearLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_book_comment, null);
        unbinder = ButterKnife.bind(this, content);
        initView();
        registerListener();



        return content;

    }

    private void registerListener() {

    }

    private void initView() {

initRecyclerView();
    }

    private void initRecyclerView() {
        comments.add(new Comment());
        comments.add(new Comment());
        comments.add(new Comment());
        BookCommentAdapter bookCommentAdapter = new BookCommentAdapter(R.layout.list_vertical_book_item, comments);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        rvListBookComment.setLayoutManager(linearLayoutManager);
        rvListBookComment.setAdapter(bookCommentAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
