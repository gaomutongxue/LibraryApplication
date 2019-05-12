package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.BaseApplication;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.util.DefaultUtils;
import com.example.xjj.libraryapplication.util.ScreenUtils;

import java.util.List;

public class NewBookAdapter extends BaseQuickAdapter <Book,BaseViewHolder>{
    public NewBookAdapter(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    public NewBookAdapter(@Nullable List<Book> data) {
        super(data);
    }

    public NewBookAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        ViewGroup.LayoutParams layoutParams = helper.getView(R.id.ll_book_list).getLayoutParams();
        layoutParams.width = (ScreenUtils.getScreenWidth(BaseApplication.baseContext) ) / 3;//
        helper.getView(R.id.ll_book_list).setLayoutParams(layoutParams);

    }
}
