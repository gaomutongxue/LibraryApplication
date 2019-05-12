package com.example.xjj.libraryapplication.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.Ui.BookManager.BookDetailActivity;

import java.util.ArrayList;

public class StartBookDetailListener implements BaseQuickAdapter.OnItemClickListener {
    private ArrayList<Book> books=new ArrayList<Book>();
    private Class<?>  thisActivity;
    private Context context;
    public StartBookDetailListener(ArrayList<Book> books, Context context){
        this.books=books;
        this.context=context;
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String name=books.get(position).getName();
        Intent intent = new Intent(context,BookDetailActivity.class);
        intent.putExtra("bookname",name);
        context.startActivity(intent);

    }
}
