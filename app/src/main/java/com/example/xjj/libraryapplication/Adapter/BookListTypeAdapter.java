package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.Enity.BookType;
import com.example.xjj.libraryapplication.R;

import java.util.List;

public class BookListTypeAdapter extends BaseQuickAdapter<BookType,BaseViewHolder> {
    public BookListTypeAdapter(int layoutResId, @Nullable List<BookType> data) {
        super(layoutResId, data);
    }

    public BookListTypeAdapter(@Nullable List<BookType> data) {
        super(data);
    }

    public BookListTypeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookType item) {
        helper.setText(R.id.bt_list_type,item.getType());
        helper.addOnClickListener(R.id.bt_list_type);

    }

}
