package com.example.xjj.libraryapplication.Ui.BookManager;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.xjj.libraryapplication.Adapter.RankAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseActivity;
import com.example.xjj.libraryapplication.common.Const;
import com.example.xjj.libraryapplication.dialog.BookTypeDialog;
import com.example.xjj.libraryapplication.event.BookTypeEvent;
import com.example.xjj.libraryapplication.listener.StartBookDetailListener;
import com.example.xjj.libraryapplication.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//TODO 当从分类中点进来的时候 应该要接收参数
public class KindBookActivity extends BaseActivity {

    @BindView(R.id.bt_kind_theme)
    Button btKindTheme;
    @BindView(R.id.bt_kind_popularwith)
    Button btKindPopularwith;
    @BindView(R.id.bt_kind_process)
    Button btKindProcess;
    @BindView(R.id.bt_kind_location)
    Button btKindLocation;
    @BindView(R.id.rv_list_kindactivity)
     RecyclerView rvListKindactivity;
    private ArrayList<Book> books=new ArrayList<Book>();
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private PopupWindow mPopWindow;
    private BookTypeDialog bookTypeDialog;
    private Bundle bundle;    // 传递给dialog的参数
    private String bookSerchRequest; //搜索的请求
    private Const aConst;
    private  RankAdapter rankAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind_book);
        ButterKnife.bind(this);
        resisterEventBus();
        initView();
        registerListener();
    }

    private void registerListener() {

    }

    private void initView() {
        bundle=new Bundle();
        initRecyclerView();

    }

    @OnClick({R.id.bt_kind_theme, R.id.bt_kind_popularwith, R.id.bt_kind_process, R.id.bt_kind_location})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.bt_kind_theme:
                bundle.putString("type","题材");
                showBookTypeDialog();
              //  rvListKindactivity.setAlpha(0.5f);
//                showPopupWindow();
                break;
            case R.id.bt_kind_popularwith:
           //     rvListKindactivity.setClickable(true);
                bundle.putString("type","受众人群");
                showBookTypeDialog();
                break;
            case R.id.bt_kind_process:
                bundle.putString("type","进度");
                showBookTypeDialog();
                break;
            case R.id.bt_kind_location:
                bundle.putString("type","地域");
                showBookTypeDialog();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void showBookTypeDialog() {
        if (bookTypeDialog==null){
            bookTypeDialog=new BookTypeDialog();
        }
        if (!bookTypeDialog.isAdded()) {
            bookTypeDialog.setArguments(bundle);
            bookTypeDialog.show(getSupportFragmentManager(), "bookTypeDialog");
        }

    }
    private void initRecyclerView() {
        String type=getIntent().getStringExtra("type");
//        Map<String, String> map = GsonUtils.getSortMap();
//        map.put("type",type);
        Book book=new Book();
        book.setType(type);
        PostRequest request = OkGo.<String>post(Const.URL+"getBookListFromType").tag(this);
        request.params("type",type);
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e("xiao====", "得到返回消息" + response.body());
                ArrayList<Book> books = GsonUtils.parseJSONArray(response.body(),new TypeToken<List<Book>>() {}.getType());
                rankAdapter = new RankAdapter(R.layout.list_vertical_book_item, books);
                linearLayoutManager= new LinearLayoutManager(getBaseContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                gridLayoutManager=new GridLayoutManager(getBaseContext(),3);
                //rvListKindactivity = (RecyclerView) findViewById(R.id.rv_list_science);
                rvListKindactivity.setLayoutManager(linearLayoutManager);
                rvListKindactivity.setAdapter(rankAdapter);
                rankAdapter.setOnItemClickListener(new StartBookDetailListener(books,getBaseContext()));
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                showToast("网络状况不佳.请重试");

            }
        });
    }

    @Subscribe
    public void onUserClickDialog(BookTypeEvent bookTypeEvent) {

        switch (bookTypeEvent.getTypeID()){
            case Const.TYPE_BOOKTHEME :
                btKindTheme.setText(bookTypeEvent.getType());
                break;
            case Const.TYPE_POPULARWITH :
                btKindPopularwith.setText(bookTypeEvent.getType());
                break;
            case Const.TYPE_PROCESS :
                btKindProcess.setText(bookTypeEvent.getType());
                break;
            case Const.TYPE_LOCATION :
                btKindLocation.setText(bookTypeEvent.getType());
                break;
        }
        startSearchBook();
    }

    private void startSearchBook() {
    //TODO 根据4个参数进行请求数据
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }



}
