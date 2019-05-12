package com.example.xjj.libraryapplication.Ui.Message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xjj.libraryapplication.Adapter.MessageAdapter;
import com.example.xjj.libraryapplication.Enity.NewMessages;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MessageFragment extends BaseFragment {

    @BindView(R.id.rv_list_message)
    RecyclerView rvListMessage;
    Unbinder unbinder;
    private ArrayList<NewMessages> newMessages=new ArrayList<NewMessages>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message, null);
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
//        newMessages.add(new NewMessages());
//        newMessages.add(new NewMessages());
//        newMessages.add(new NewMessages());
        messageAdapter = new MessageAdapter(R.layout.list_vertical_book_item, newMessages);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListMessage.setLayoutManager(linearLayoutManager);
        rvListMessage.setAdapter(messageAdapter);

        messageAdapter.setEmptyView(getEmptyView());

    }

    public View getEmptyView() {
        View view=View.inflate(getContext(),R.layout.list_default_empty,null);
        return view;
    }
}
