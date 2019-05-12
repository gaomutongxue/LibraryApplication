package com.example.xjj.libraryapplication.Adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xjj.libraryapplication.Enity.NewMessages;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<NewMessages,BaseViewHolder> {
    public MessageAdapter(int layoutResId, @Nullable List<NewMessages> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewMessages item) {

    }
}
