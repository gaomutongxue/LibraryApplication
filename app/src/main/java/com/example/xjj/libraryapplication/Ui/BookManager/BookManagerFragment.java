package com.example.xjj.libraryapplication.Ui.BookManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xjj.libraryapplication.Adapter.BookPageAdapter;
import com.example.xjj.libraryapplication.R;
import com.example.xjj.libraryapplication.Ui.BaseFragment;
import com.example.xjj.libraryapplication.Ui.bookpager.ClassificationPager;
import com.example.xjj.libraryapplication.Ui.bookpager.RankPager;
import com.example.xjj.libraryapplication.Ui.bookpager.RecommendPager;
import com.example.xjj.libraryapplication.event.MessageEvent;
import com.mob.wrappers.UMSSDKWrapper;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BookManagerFragment extends BaseFragment {
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.bt_change_rv_state)
    Button btChangeRvState;
    @BindView(R.id.bt_search)
    Button btSearch;
    private FragmentManager fragmentManager;
    private BookPageAdapter bookPageAdapter;
    private ArrayList<String> mTitleDataList;
    private ArrayList<BaseFragment> baseFragments;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        content = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_book_manager, null);
        unbinder = ButterKnife.bind(this, content);
        initView(content);
        registerListener(content);


        return content;

    }

    private void registerListener(View content) {
        btChangeRvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aaaaa", "aaaa");

                MessageEvent messageEvent = new MessageEvent();
                messageEvent.message = "aaa";
                EventBus.getDefault().post(messageEvent);
                // toStartActivity();

            }
        });

    }

    private void toStartActivity() {
        Intent intent = new Intent(getActivity(), SerchActivity.class);
        startActivity(intent);
    }

    private void initView(View content) {
        btChangeRvState = (Button) content.findViewById(R.id.bt_change_rv_state);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentManager = getFragmentManager();
        baseFragments = new ArrayList<BaseFragment>();
        initTitleDataList();
        initViewPage();
        initMagicIndicatior();

    }

    private void initTitleDataList() {
        mTitleDataList = new ArrayList<String>();

        for (String a : getResources().getStringArray(R.array.book_page_title)) {
            mTitleDataList.add(a);
        }
    }

    private void initViewPage() {
        baseFragments.add(new RankPager());
        baseFragments.add(new ClassificationPager());
        baseFragments.add(new RecommendPager());
        bookPageAdapter = new BookPageAdapter(fragmentManager, baseFragments);
        viewPager.setAdapter(bookPageAdapter);


    }

    private void initMagicIndicatior() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
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
                        viewPager.setCurrentItem(i);
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
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    public static BaseFragment newInstance(int position) {
        BookManagerFragment fragment = new BookManagerFragment();
        Bundle bundle = new Bundle();
        Log.d("ccccc", "ccccc");
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.bt_search)
    public void onSearchClick() {
        toStartActivity();
    }
}
