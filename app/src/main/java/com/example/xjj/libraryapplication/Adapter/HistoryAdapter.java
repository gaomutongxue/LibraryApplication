package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<Book,BaseViewHolder> {
    public HistoryAdapter(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    public HistoryAdapter(@Nullable List<Book> data) {
        super(data);
    }

    public HistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        helper.setText(R.id.bt_history_item, item.name);

    }
}
