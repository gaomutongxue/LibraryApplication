package com.example.xjj.libraryapplication.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xjj.libraryapplication.Adapter.BookListTypeAdapter;
import com.example.xjj.libraryapplication.BaseApplication;
import com.example.xjj.libraryapplication.Enity.BookType;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.common.Const;
import com.example.xjj.libraryapplication.event.BookTypeEvent;
import com.example.xjj.libraryapplication.util.DefaultUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookTypeDialog extends DialogFragment {
    @BindView(R.id.rv_list_book_typer)
    RecyclerView rvListBookTyper;
    Unbinder unbinder;


    private ArrayList<BookType> list_type;
    private String data01;   //传入的参数
    private GridLayoutManager gridLayoutManager;
    private BookType bookType;
    private BookListTypeAdapter bookListTypeAdapter;
    private int bookTypeID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.from(getActivity()).inflate(R.layout.dialog_book_type, container);
        unbinder = ButterKnife.bind(this, view);
        initView();
        registerListener();
        return view;

    }

    private void registerListener() {
        bookListTypeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BookTypeEvent bookTypeEvent = new BookTypeEvent();
                bookTypeEvent.setType(list_type.get(position).getType());
                bookTypeEvent.setTypeID(bookTypeID);
                EventBus.getDefault().post(bookTypeEvent);
                dismiss();
            }
        });

        bookListTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("aaaaa", "aaaaaa");

            }
        });

    }

    private void initView() {
        initListType();
        initRecyclerView();
    }

    private void initListType() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            data01 = bundle.getString("type");
        }
        list_type = new ArrayList<BookType>();
        switch (data01) {
            case "题材":
                bookTypeID = Const.TYPE_BOOKTHEME;
                for (String a : getResources().getStringArray(R.array.book_type_theme)) {
                    bookType = new BookType();
                    bookType.setType(a);
                    list_type.add(bookType);
                }
                break;
            case "受众人群":
                bookTypeID = Const.TYPE_POPULARWITH;
                for (String a : getResources().getStringArray(R.array.book_type_popularwith)) {
                    bookType = new BookType();
                    bookType.setType(a);
                    list_type.add(bookType);
                }
                break;
            case "进度":
                bookTypeID = Const.TYPE_PROCESS;
                for (String a : getResources().getStringArray(R.array.book_type_process)) {
                    bookType = new BookType();
                    bookType.setType(a);
                    list_type.add(bookType);
                }
                break;
            case "地域":
                bookTypeID = Const.TYPE_LOCATION;
                for (String a : getResources().getStringArray(R.array.book_type_location)) {
                    bookType = new BookType();
                    bookType.setType(a);
                    list_type.add(bookType);
                }
                break;

        }

    }

    private void initRecyclerView() {
        bookListTypeAdapter = new BookListTypeAdapter(R.layout.type_list_item, list_type);
        gridLayoutManager = new GridLayoutManager(getContext(), 5);
        rvListBookTyper.setLayoutManager(gridLayoutManager);
        rvListBookTyper.setAdapter(bookListTypeAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        设置窗口顶格
         */
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backgroud)));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.y = DefaultUtils.dp2px(BaseApplication.baseContext, 40);
        layoutParams.x = 0;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = 500;
        layoutParams.dimAmount = 0f;

        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        window.setAttributes(layoutParams);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
        unbinder.unbind();

    }

    public void resisterEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            //加上判断
            EventBus.getDefault().register(this);
        }
    }


}
