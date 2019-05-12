package com.example.xjj.libraryapplication.Ui.BookManager;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xjj.libraryapplication.Adapter.HistoryAdapter;
import com.example.xjj.libraryapplication.Adapter.RankAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseActivity;
import com.example.xjj.libraryapplication.common.Const;
import com.example.xjj.libraryapplication.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SerchActivity extends BaseActivity {


    @BindView(R.id.search_bar)
    MaterialSearchBar searchBar;
    @BindView(R.id.rv_list_history)
    RecyclerView rvListHistory;
    @BindView(R.id.rv_list_result)
    RecyclerView rvListResult;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Book> hisbooks = new ArrayList<Book>();
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private HistoryAdapter historyAdapter;
    private RankAdapter rankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        ButterKnife.bind(this);
        initView();
        registerListener();
    }

    private void registerListener() {
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                tvHistory.setVisibility(View.GONE);
                rvListHistory.setVisibility(View.GONE);

                Log.d("开始搜索", "去吧");
                final PostRequest request = OkGo.<String>post(Const.URL+"seletbooksbyname").tag(this);
                request.params("name", text.toString());
                request.execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("xiao====", "得到返回消息" + response.body());
                        if (response.body().equals("fail")) {
                            showToast("查询失败.请重试");
                        }else {
                            books = GsonUtils.parseJSONArray(response.body(),new TypeToken<List<Book>>() {}.getType());
//                        historyAdapter.addData();
                            rankAdapter.setNewData(books);
                            rankAdapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("网络状况不佳.请重试");

                    }
                });
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void initView() {
        initRv();
    }

    private void initRv() {
        initBooks();
        rankAdapter =new RankAdapter(R.layout.list_vertical_book_item,books);

        historyAdapter = new HistoryAdapter(R.layout.list_history_item, hisbooks);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager = new GridLayoutManager(this, 3);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//        rvListHistory = (RecyclerView) findViewById(R.id.rv_list_science);
        rvListHistory.setLayoutManager(staggeredGridLayoutManager);
        rvListHistory.setAdapter(historyAdapter);
        rvListResult.setLayoutManager(linearLayoutManager);
        rvListResult.setAdapter(rankAdapter);
        historyAdapter.setUpFetchEnable(true);
        historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("xiao===",hisbooks.get(position).getName());
                searchBar.setText(hisbooks.get(position).getName());

            }
        });
    }

    private void initBooks() {
        hisbooks.add(new Book("论成功"));
        hisbooks.add(new Book("论如何考研"));
    }


}
