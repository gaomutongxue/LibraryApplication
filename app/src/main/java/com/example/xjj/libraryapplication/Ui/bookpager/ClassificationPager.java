package com.example.xjj.libraryapplication.Ui.bookpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xjj.libraryapplication.Adapter.ClassificationpagerAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.Enity.BookClissification;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;
import com.example.xjj.libraryapplication.Ui.BookManager.KindBookActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ClassificationPager extends BaseFragment {
    @BindView(R.id.rv_list_classfication)
    RecyclerView rvListClassfication;
    Unbinder unbinder;
    ClassificationpagerAdapter classificationpagerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private boolean isRvStateVertical =true;
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<BookClissification> mClassifications ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_classfication_pager, null);
        unbinder = ButterKnife.bind(this, content);
        initView(content);
        registerListener(content);


        return content;

    }

    private void registerListener(View content) {

        classificationpagerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String type=getResources().getStringArray(R.array.book_kinds)[position];
                Intent intent =new Intent(getActivity(), KindBookActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
    }
    private void initView(View content) {
        books=new ArrayList<Book>();
        initRecyclerView();
    }
    private void initRecyclerView() {
        initClassifications();
        classificationpagerAdapter = new ClassificationpagerAdapter(R.layout.list_book_item, mClassifications);
        linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager=new GridLayoutManager(getContext(),3);
//        rvListClassfication = (RecyclerView) findViewById(R.id.rv_list_classfication);
        rvListClassfication.setLayoutManager(gridLayoutManager);
        rvListClassfication.setAdapter(classificationpagerAdapter);


    }
    private void initClassifications() {
        mClassifications = new ArrayList<BookClissification>();

        for (String a : getResources().getStringArray(R.array.book_kinds)) {
            BookClissification bookClissification=new BookClissification();
            bookClissification.setKind(a);
            mClassifications.add(bookClissification);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
