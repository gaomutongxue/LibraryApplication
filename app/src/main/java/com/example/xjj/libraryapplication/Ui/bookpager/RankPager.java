package com.example.xjj.libraryapplication.Ui.bookpager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.xjj.libraryapplication.Adapter.RankAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;
import com.example.xjj.libraryapplication.common.Const;
import com.example.xjj.libraryapplication.common.Waiting;
import com.example.xjj.libraryapplication.event.MessageEvent;
import com.example.xjj.libraryapplication.listener.StartBookDetailListener;
import com.example.xjj.libraryapplication.util.GsonUtils;
import com.example.xjj.libraryapplication.util.ToastUtils;
import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.rotationheader.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
changeRvState 订阅了改变list状态的方法
 */
public class RankPager extends BaseFragment {
    ArrayList<Book> books = new ArrayList<Book>();
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rv_list_science)
    RecyclerView rvListScience;
    Unbinder unbinder;
    @BindView(R.id.sv_rv_science)
    SpringView svRvScience;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private boolean isRvStateVertical = true;
    private RankAdapter rankAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_science_pager, null);
        unbinder = ButterKnife.bind(this, content);
        initView(content);
        registerListener(content);

        resisterEventBus();
        return content;

    }

    private void registerListener(View content) {

    }

    private void initView(View content) {
        initSpringView();
        initRecyclerView();
    }

    private void initSpringView() {
        svRvScience.setType(SpringView.Type.FOLLOW);
        svRvScience.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetRequest request = OkGo.<String>get(Const.URL+"getbooks").tag(this);
                        request.execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                ArrayList<Book> books= GsonUtils.parseJSONArray(response.body(), new TypeToken<List<Book>>() {}.getType());
                                Collections.reverse(books);
                               rankAdapter.setNewData(books);
                               rankAdapter.notifyDataSetChanged();
                               svRvScience.onFinishFreshAndLoad();

                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                ToastUtils.showShort(getContext(),"网络状况不佳");
                            }
                        });
                    }
                }, 500);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        svRvScience.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        svRvScience.setHeader(new RotationHeader(getContext()));
        svRvScience.setFooter(new RotationHeader(getContext()));
    }

    private void initRecyclerView() {
        final Waiting waiting=new Waiting(getContext());
        waiting.showWaiting(false);
        GetRequest request = OkGo.<String>get(Const.URL+"getbooks").tag(this);
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                waiting.hideWaiting();
                ArrayList<Book> books= GsonUtils.parseJSONArray(response.body(), new TypeToken<List<Book>>() {}.getType());
                rankAdapter = new RankAdapter(R.layout.list_vertical_book_item, books);
                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setStackFromEnd(true);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                gridLayoutManager = new GridLayoutManager(getContext(), 3);
                rvListScience = (RecyclerView) findViewById(R.id.rv_list_science);
                rvListScience.setLayoutManager(linearLayoutManager);
                rvListScience.setAdapter(rankAdapter);
                rvListScience.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                rankAdapter.setOnItemClickListener(new StartBookDetailListener(books,getContext()));

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtils.showShort(getContext(),"网络状况不佳");
            }
        });
    }

    @Subscribe
    public void changeRvState(MessageEvent messageEvent) {
        Log.d("改变rv状态按钮", "被碰到了");
        if (isRvStateVertical) {
            rvListScience.setLayoutManager(gridLayoutManager);
            isRvStateVertical = false;
        } else {
            rvListScience.setLayoutManager(linearLayoutManager);
            isRvStateVertical = true;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
