package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.Enity.Comment;

import java.util.List;

public class BookCommentAdapter extends BaseQuickAdapter<Comment,BaseViewHolder>{
    public BookCommentAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    public BookCommentAdapter(@Nullable List<Comment> data) {
        super(data);
    }

    public BookCommentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {

    }
}
