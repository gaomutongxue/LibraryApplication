package com.example.xjj.libraryapplication.Ui.BookManager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.xjj.libraryapplication.Adapter.BookPageAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseActivity;
import com.example.xjj.libraryapplication.Ui.BaseFragment;
import com.example.xjj.libraryapplication.Ui.bookpager.BookCommentPager;
import com.example.xjj.libraryapplication.Ui.bookpager.BookDescribePager;
import com.example.xjj.libraryapplication.common.Const;
import com.example.xjj.libraryapplication.common.Waiting;
import com.example.xjj.libraryapplication.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_bookdetial)
    Toolbar toolbarBookdetial;
    @BindView(R.id.collapsing_toolbar_book)
    CollapsingToolbarLayout collapsingToolbarBook;
    @BindView(R.id.appbar_bookdetail)
    AppBarLayout appbarBookdetail;
    @BindView(R.id.magic_indicator_book)
    MagicIndicator magicIndicatorBook;
    @BindView(R.id.vp_book_detail)
    ViewPager vpBookDetail;
    private ArrayList<String> mTitleDataList;
    private ArrayList<BaseFragment> baseFragments=new ArrayList<BaseFragment>();
    private BookPageAdapter bookPageAdapter;
    private String name ; //由外部传入bookid指定显示的书籍
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        initView();
        registerListener();

    }

    private void registerListener() {


    }

    private void initView() {
        waiting=new Waiting(this);
        waiting.showWaiting(false);
        name=getIntent().getStringExtra("bookname");
        mTitleDataList=new ArrayList<String>();
        getBook();
        initViewPage();
        initMagicIndicatior();
        requestInfo();
    }

    private void requestInfo() {
        PostRequest request = OkGo.<String>post(Const.URL+"seletbooksbyname").tag(this);
        request.params("name",name);
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                waiting.hideWaiting();
                Log.e("xiao====", "得到返回消息" + response.body());
                if (response.body().equals("fail")) {
                    showToast("查询失败.请重试");
                }else {
                    ArrayList<Book> books = GsonUtils.parseJSONArray(response.body(),new TypeToken<List<Book>>() {}.getType());
                    if (books.size()>0) {
                        book=books.get(0);
                        collapsingToolbarBook.setTitle(book.name);
                        EventBus.getDefault().post(book);
                    }
                }
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                showToast("网络状况不佳.请重试");
            }
        });
    }

    private void initMagicIndicatior() {

            mTitleDataList.add("书本详情");
            mTitleDataList.add("书本评论");
            MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator_book);
            CommonNavigator commonNavigator = new CommonNavigator(this);
            commonNavigator.setAdjustMode(true);
            commonNavigator.setAdapter(new CommonNavigatorAdapter() {
                @Override
                public int getCount() {
                    return mTitleDataList == null ? 0 : mTitleDataList.size();
                }

                @Override
                public IPagerTitleView getTitleView(Context context, final int i) {
                    ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                    colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                    colorTransitionPagerTitleView.setTextSize(20);
                    colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                    colorTransitionPagerTitleView.setText(mTitleDataList.get(i));
                    colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("dianji", String.valueOf(i));
                            vpBookDetail.setCurrentItem(i);
                        }
                    });
                    return colorTransitionPagerTitleView;
                }

                @Override
                public IPagerIndicator getIndicator(Context context) {
                    LinePagerIndicator indicator = new LinePagerIndicator(context);
                    indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                    return indicator;
                }
            });
            magicIndicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(magicIndicator, vpBookDetail);

        }
    private void initViewPage() {
        baseFragments.add(new BookDescribePager());
        baseFragments.add(new BookCommentPager());
        bookPageAdapter = new BookPageAdapter(getSupportFragmentManager(), baseFragments);
        vpBookDetail.setAdapter(bookPageAdapter);


    }

    public void getBook() {
        //TODO 用外部传入的ID得到相应的书本实体
    }
}
