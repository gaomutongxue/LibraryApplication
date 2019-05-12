package com.example.xjj.libraryapplication.Ui.index;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.example.xjj.libraryapplication.Adapter.GlideImageLoader;
import com.example.xjj.libraryapplication.Adapter.NewBookAdapter;
import com.example.xjj.libraryapplication.Adapter.NoticeListAdapter;
import com.example.xjj.libraryapplication.Enity.Book;
import com.example.xjj.libraryapplication.Enity.Notice;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;
import com.example.xjj.libraryapplication.common.Waiting;
import com.example.xjj.libraryapplication.listener.StartBookDetailListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.disposables.CancellableDisposable;

public class IndexFragment extends BaseFragment implements ViewPager.OnPageChangeListener{



    @BindView(R.id.animator_text)
    ViewAnimator animatorText;
    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_new_book)
    RecyclerView rvNewBook;
    @BindView(R.id.rv_list_notic)
    RecyclerView rvListNotic;
    private boolean autoPlayFlag = true;
    NewBookAdapter newBookAdapter;
    NoticeListAdapter noticeListAdapter;
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<Notice> notices =new ArrayList<Notice>();

    Integer[] images = new Integer[]{R.mipmap.ic_home, R.mipmap.ic_home, R.mipmap.ic_home};
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (autoPlayFlag) {
                showNext();
            }

            handler.sendMessageDelayed(new Message(), 3000);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index_layout, null);
        initView(content);
        registerListener(content);
        unbinder = ButterKnife.bind(this, content);
        return content;

    }

    public void onResume() {
        super.onResume();
        /*
        发送信息开启滚动
         */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);

            }
        }, 3000);
    }

    private void registerListener(View content) {

        newBookAdapter.setOnItemClickListener(new StartBookDetailListener(books,getContext()));
//        newBookAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                String id= books.get(position).getID();
//                toStartActivityExtra(BookDetailActivity.class,getString(R.string.bookID),id);
//
//            }
//        });
    }

    private void initView(View content) {

        banner = (Banner) findViewById(R.id.banner);
        banner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoader()).start();

        initNewBookList();
        initNoticeList();

    }

    private void initNewBookList() {
        books.add(new Book("aaa"));
        books.add(new Book("aaa"));
        books.add(new Book("aaa"));
        newBookAdapter = new NewBookAdapter(R.layout.list_book_item, books);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvNewBook = (RecyclerView) findViewById(R.id.rv_new_book);
        rvNewBook.setLayoutManager(linearLayoutManager);
        rvNewBook.setAdapter(newBookAdapter);
        rvNewBook.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }
    private void initNoticeList() {
        notices.add(new Notice("aaa"));
        notices.add(new Notice("aaa"));
        notices.add(new Notice("aaa"));
        noticeListAdapter = new NoticeListAdapter(R.layout.list_notice_item, notices);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListNotic = (RecyclerView) findViewById(R.id.rv_list_notic);
        rvListNotic.setLayoutManager(linearLayoutManager);
        rvListNotic.setAdapter(noticeListAdapter);
        rvListNotic.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }
    /*
   控制文字滚动
    */
    public void showNext() {
        animatorText.setOutAnimation(getContext(), R.anim.slide_out_up);
        animatorText.setInAnimation(getContext(), R.anim.slide_in_down);
        animatorText.showNext();
    }

    public void showPrevious() {
        animatorText.setOutAnimation(getContext(), R.anim.slide_out_down);
        animatorText.setInAnimation(getContext(), R.anim.slide_in_up);
        animatorText.showPrevious();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public static Fragment newInstance(int position) {
        IndexFragment fragment = new IndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position==(images.length-1)){

        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
