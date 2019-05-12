package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.Enity.Notice;

import java.util.List;

public class NoticeListAdapter extends BaseQuickAdapter<Notice,BaseViewHolder> {
    public NoticeListAdapter(int layoutResId, @Nullable List<Notice> data) {
        super(layoutResId, data);
    }

    public NoticeListAdapter(@Nullable List<Notice> data) {
        super(data);
    }

    public NoticeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Notice item) {

    }


}
