package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;

import java.util.List;

public class RankAdapter extends BaseQuickAdapter<Book,BaseViewHolder> {
    public RankAdapter(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    public RankAdapter(@Nullable List<Book> data) {
        super(data);
    }

    public RankAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        helper.setText(R.id.book_name,item.getName());
        helper.setText(R.id.zuozhe_name,item.getAutor());
    }
}
