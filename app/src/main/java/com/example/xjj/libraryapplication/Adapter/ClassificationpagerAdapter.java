package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.Enity.BookClissification;
import com.example.xjj.libraryapplication.R;

import java.util.List;

public class ClassificationpagerAdapter extends BaseQuickAdapter<BookClissification,BaseViewHolder> {

    public ClassificationpagerAdapter(int layoutResId, @Nullable List<BookClissification> data) {
        super(layoutResId, data);
    }

    public ClassificationpagerAdapter(@Nullable List<BookClissification> data) {
        super(data);
    }

    public ClassificationpagerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookClissification item) {
      helper.setText(R.id.tv_list_book_name,item.getKind());
    }
}
